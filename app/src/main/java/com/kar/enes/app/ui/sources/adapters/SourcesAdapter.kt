package com.kar.enes.app.ui.sources.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.kar.enes.app.BR
import com.kar.enes.app.R
import com.kar.enes.app.data.model.SourceModel
import com.kar.enes.app.databinding.ItemSourcesBinding
import com.kar.enes.app.ui.sources.SourcesViewModel
import com.kar.enes.app.utils.DataBindingViewHolder

/**
 * Created by M.Enes on 1/2/2020.
 */
class SourcesAdapter( private var sourceList: ArrayList<SourceModel>,
                      private val sourcesViewModel: SourcesViewModel): RecyclerView.Adapter<SourcesAdapter.ViewHolder>() {

    inner class ViewHolder(binding: ViewDataBinding): DataBindingViewHolder<SourceModel>(binding){
        override fun onBind(t: SourceModel,position: Int) {
            dataBinding.setVariable(BR.viewModel,sourcesViewModel)
            dataBinding.setVariable(BR.item,t)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemSourcesBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return sourceList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(sourceList[position],position)
    }

    fun setSourceList(list: ArrayList<SourceModel>){
        sourceList = list
    }
}