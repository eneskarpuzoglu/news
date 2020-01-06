package com.kar.enes.app.ui.sources

import android.app.Activity
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.kar.enes.app.BR
import com.kar.enes.app.R
import com.kar.enes.app.data.model.SourceModel
import com.kar.enes.app.databinding.ActivitySourcesBinding
import com.kar.enes.app.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_sources.*
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

/**
 * Created by M.Enes on 1/2/2020.
 */
class SourcesActivity : BaseActivity<ActivitySourcesBinding,SourcesViewModel>(), SourcesNavigator,SearchView.OnQueryTextListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var sourcesViewModel: SourcesViewModel

    override fun getLayoutId(): Int {
        return R.layout.activity_sources
    }

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun getViewModel(): SourcesViewModel {
        sourcesViewModel = ViewModelProviders.of(this, viewModelFactory).get(SourcesViewModel::class.java)
        return sourcesViewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sourcesViewModel.setNavigator(this)
        val layoutManager = LinearLayoutManager(applicationContext)
        rvSources.layoutManager = layoutManager
        sourcesViewModel.loader.observe(this,loaderObserver)
        sourcesViewModel.findSources()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        val menuItem = menu.findItem(R.id.action_search)
        val searchView = menuItem.actionView as SearchView
        searchView.setOnQueryTextListener(this)
        return true
    }

    override fun getActivity(): Activity {
        return this@SourcesActivity
    }

    override fun stopRefreshing() {
        srfSources.isRefreshing = false
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
        //action bar'da arama kismina yazilan yaziya gore kaynaklarda arama yapar
        val text = newText.toLowerCase(Locale.getDefault())
        val list: ArrayList<SourceModel> = ArrayList()
        for (source in sourcesViewModel.sources.value!!){
            if (source.name.toLowerCase(Locale.getDefault()).contains(text)){
                list.add(source)
            }
        }
        sourcesViewModel.setAdapter(list)
        return true
    }
}
