package com.kar.enes.app.ui.sources

import android.app.Activity
import android.content.Intent
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import com.kar.enes.app.R
import com.kar.enes.app.data.DataManager
import com.kar.enes.app.data.model.SourceModel
import com.kar.enes.app.data.model.api.ErrorData
import com.kar.enes.app.data.model.response.SourceResponse
import com.kar.enes.app.data.remote.ApiObserver
import com.kar.enes.app.ui.base.BaseViewModel
import com.kar.enes.app.ui.news.NewsActivity
import com.kar.enes.app.ui.sources.adapters.SourcesAdapter
import com.kar.enes.app.utils.AppConstants
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.toast
import javax.inject.Inject

/**
 * Created by M.Enes on 1/2/2020.
 */
class SourcesViewModel @Inject constructor(dataManager: DataManager): BaseViewModel<SourcesNavigator>(dataManager) {

    var sourcesAdapter: SourcesAdapter = SourcesAdapter(ArrayList(),this)
    var sources = MutableLiveData<ArrayList<SourceModel>>()
    var showEmpty: ObservableBoolean = ObservableBoolean(false)


    fun findSources(){
        displayLoader(true)
        dataManager.getSources(AppConstants.API_KEY)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object: ApiObserver<SourceResponse>(compositeDisposable){
                override fun onSuccess(data: SourceResponse) {
                    getNavigator()!!.stopRefreshing()
                    displayLoader(false)
                    if (data.status == "ok"){
                        if (!data.sources.isNullOrEmpty()){
                            sources.value = data.sources!!
                            setAdapter(data.sources!!)
                            showEmpty.set(false)
                        }
                        else {
                            activity().toast(activity().getString(R.string.sources_not_found))
                            showEmpty.set(true)
                        }
                    }else{
                        activity().toast(data.message!!)
                        showEmpty.set(true)
                    }
                }

                override fun onError(e: ErrorData) {
                    getNavigator()!!.stopRefreshing()
                    displayLoader(false)
                    activity().toast(e.message)
                }

            })
    }

    fun clickSource(item: SourceModel){
        val intent = Intent(activity(),NewsActivity::class.java)
        intent.putExtra("id",item.id)
        activity().startActivity(intent)
    }

    fun setAdapter(list: ArrayList<SourceModel>){
        sourcesAdapter.setSourceList(list)
        sourcesAdapter.notifyDataSetChanged()
    }

    fun getAdapter(): SourcesAdapter{
        return sourcesAdapter
    }

    private fun activity():Activity{
        return getNavigator()!!.getActivity()
    }

    fun getSourcesAt(position: Int): SourceModel{
        if (sources.value != null && sources.value!!.isNotEmpty() && position >= 0 && position<sources.value!!.size){
            return sources.value!![position]
        }
        return SourceModel()
    }
}