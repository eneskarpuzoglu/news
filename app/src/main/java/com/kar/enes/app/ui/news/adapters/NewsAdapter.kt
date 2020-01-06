package com.kar.enes.app.ui.news.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.kar.enes.app.BR
import com.kar.enes.app.data.model.ArticleModel
import com.kar.enes.app.databinding.ItemNewsBinding
import com.kar.enes.app.ui.news.NewsViewModel
import com.kar.enes.app.utils.DataBindingViewHolder

/**
 * Created by M.Enes on 1/3/2020.
 */
class NewsAdapter (private var newsList: ArrayList<ArticleModel>,
                   private val newsViewModel: NewsViewModel): RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    inner class ViewHolder(binding: ViewDataBinding): DataBindingViewHolder<ArticleModel>(binding){
        override fun onBind(t: ArticleModel,position: Int) {
            dataBinding.setVariable(BR.viewModel,newsViewModel)
            dataBinding.setVariable(BR.item,t)
            dataBinding.setVariable(BR.position,position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemNewsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(newsList[position],position)
    }

    fun setNewsList(list: ArrayList<ArticleModel>){
        newsList = list
    }

    fun getNewsList(): ArrayList<ArticleModel>{
        return newsList
    }
}