package com.kar.enes.app.ui.news

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.kar.enes.app.BR
import com.kar.enes.app.R
import com.kar.enes.app.data.model.ArticleModel
import com.kar.enes.app.data.model.SourceModel
import com.kar.enes.app.databinding.ActivityNewsBinding
import com.kar.enes.app.ui.base.BaseActivity
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_news.*
import kotlinx.android.synthetic.main.activity_sources.*
import timber.log.Timber
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.collections.ArrayList

class NewsActivity : BaseActivity<ActivityNewsBinding,NewsViewModel>(),NewsNavigator,SearchView.OnQueryTextListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var newsViewModel: NewsViewModel
    private var running = false

    override fun getLayoutId(): Int {
        return R.layout.activity_news
    }

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun getViewModel(): NewsViewModel {
        newsViewModel = ViewModelProviders.of(this, viewModelFactory).get(NewsViewModel::class.java)
        return newsViewModel
    }

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        actionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        newsViewModel.setNavigator(this)
        newsViewModel.sourceId = intent.getStringExtra("id")
        val layoutManager = LinearLayoutManager(applicationContext)
        rvNews.layoutManager = layoutManager
        newsViewModel.loader.observe(this,loaderObserver)
        newsViewModel.getArticles(false)

        //Her 1 dakikada bir yeni haber var mi kontrol eder
        Observable.interval(1,TimeUnit.MINUTES)
            .subscribeOn(AndroidSchedulers.mainThread())
            .observeOn(Schedulers.io())
            .takeWhile{ running }
            .subscribe{
                newsViewModel.getArticles(true)
            }
    }

    override fun onResume() {
        super.onResume()
        running = true
    }

    override fun onPause() {
        super.onPause()
        running = false
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        val menuItem = menu.findItem(R.id.action_search)
        val searchView = menuItem.actionView as SearchView
        searchView.setOnQueryTextListener(this)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun getActivity(): Activity {
        return this@NewsActivity
    }

    override fun stopRefreshing() {
        srfNews.isRefreshing = false
    }

    override fun updateRv(list: ArrayList<ArticleModel>, exSize: Int) {
        // recylerview pozisyonununda en ustte gorunen haberin pozisyonunu alip yeni listede ayni habere scroll etmesini saglar
        val currentVisiblePosition = (rvNews.layoutManager as LinearLayoutManager).findFirstCompletelyVisibleItemPosition()
        newsViewModel.newsAdapter.setNewsList(list)
        (rvNews.layoutManager as LinearLayoutManager).scrollToPosition(currentVisiblePosition+(list.size-exSize))
    }

    private val loaderObserver = Observer<Boolean> { value ->
        // loader'in degerine gore ekranda progress bar cikarir
        value.let {
            if (it) showProgress()
            else dismissProgress()
        }
    }

    override fun onQueryTextSubmit(query: String): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String): Boolean {
        val text = newText.toLowerCase(Locale.getDefault())
        val list: ArrayList<ArticleModel> = ArrayList()
        for (article in newsViewModel.news.value!!){
            if (article.title.toLowerCase(Locale.getDefault()).contains(text)){
                list.add(article)
            }
        }
        newsViewModel.setAdapter(list)
        return true
    }
}
