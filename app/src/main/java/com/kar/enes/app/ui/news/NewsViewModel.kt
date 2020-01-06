package com.kar.enes.app.ui.news

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import com.kar.enes.app.R
import com.kar.enes.app.data.DataManager
import com.kar.enes.app.data.model.ArticleModel
import com.kar.enes.app.data.model.SourceModel
import com.kar.enes.app.data.model.api.ErrorData
import com.kar.enes.app.data.model.response.HeadLinesResponse
import com.kar.enes.app.data.remote.ApiObserver
import com.kar.enes.app.ui.base.BaseViewModel
import com.kar.enes.app.ui.news.adapters.NewsAdapter
import com.kar.enes.app.ui.sources.adapters.SourcesAdapter
import com.kar.enes.app.utils.AppConstants
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.toast
import javax.inject.Inject

/**
 * Created by M.Enes on 1/3/2020.
 */
class NewsViewModel @Inject constructor(dataManager: DataManager): BaseViewModel<NewsNavigator>(dataManager) {

    var newsAdapter = NewsAdapter(ArrayList(),this)
    var news = MutableLiveData<ArrayList<ArticleModel>>()
    var sourceId = ""
    var showEmpty: ObservableBoolean = ObservableBoolean(false)

    fun refreshRv(){
        getArticles(false)
    }
    fun getArticles(auto: Boolean){
        if (!auto) displayLoader(true)
        dataManager.getTopHeadlines(AppConstants.API_KEY,sourceId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object: ApiObserver<HeadLinesResponse>(compositeDisposable){
                override fun onSuccess(data: HeadLinesResponse) {
                    if (!auto) displayLoader(false)
                    getNavigator()!!.stopRefreshing()
                    if (data.status == "ok"){
                        if (data.articles != null){
                            news.value = data.articles!!
                            if (!auto){
                                // otomatik yapilan yenileme degilse adapter'i yenile
                                setAdapter(data.articles!!)
                            }else{
                                // otomatik yapilan yenileme ise yeni haber cikmis mi kontrol et
                                controlArticles(data.articles!!,newsAdapter.getNewsList())

                            }
                            setAdapter(data.articles!!)
                            showEmpty.set(false)
                        }else{
                            activity().toast(activity().getString(R.string.news_not_found))
                            showEmpty.set(true)
                        }
                    }else{
                        activity().toast(data.message!!)
                        showEmpty.set(true)
                    }
                }

                override fun onError(e: ErrorData) {
                    getNavigator()!!.stopRefreshing()
                    if (!auto) displayLoader(false)
                    activity().toast(e.message)
                }


            })
    }

    fun setAdapter(list: ArrayList<ArticleModel>){
        newsAdapter.setNewsList(list)
        newsAdapter.notifyDataSetChanged()
    }

    fun getAdapter(): NewsAdapter {
        return newsAdapter
    }

    fun clickNews(position: Int){
        //activity().toast(news.value!![position].title)
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(news.value!![position].url))
        activity().startActivity(intent)
    }

    fun clickReadList(position: Int){
        //okuma listesini kontrol et bos ise tiklanani okuma listesine ekle
        //bos degilse okuma listesinde mi kontrol et okuma listesinde degilse ekle, okuma listesindeyse sil
        //adapterin tiklanan pozisyonundaki itemin view'ini yenile
        if (!dataManager.getReadList().isNullOrEmpty()){
            var bool = false
            for (url in dataManager.getReadList()!!){
                if (url == news.value!![position].url){
                    bool = true
                }
            }
            if (bool){
                removeReadList(news.value!![position].url)
            }else{
                addReadList(news.value!![position].url)
            }
        }else{
            addReadList(news.value!![position].url)
        }
        newsAdapter.notifyItemChanged(position)
    }

    private fun addReadList(url: String){
        //url'i okuma listesine ekle
        val readList = dataManager.getReadList()!!
        readList.add(url)
        dataManager.setReadList(readList)
    }

    private fun removeReadList(url: String){
        //url'i okuma listesinden sil
        val readList = dataManager.getReadList()!!
        readList.remove(url)
        dataManager.setReadList(readList)
    }

    private fun controlArticles(newList: ArrayList<ArticleModel>,exList: ArrayList<ArticleModel>){
        if (newList.size == exList.size) {
            return
        }
        getNavigator()!!.updateRv(newList,exList.size)
        activity().toast(activity().getString(R.string.new_news_added))
    }

    fun favText(item: ArticleModel): String{
        //okuma listesinde olup olmadigina gore text dondurur
        var text = activity().getString(R.string.add_read_list)
        if (!dataManager.getReadList().isNullOrEmpty()) {
            for (url in dataManager.getReadList()!!){
                if ( url == item.url){
                    text = activity().getString(R.string.remove_read_list)
                }
            }
        }
        return text
    }

    fun activity(): Activity{
        return getNavigator()!!.getActivity()
    }
}