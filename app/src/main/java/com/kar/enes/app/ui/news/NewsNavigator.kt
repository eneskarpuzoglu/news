package com.kar.enes.app.ui.news

import android.app.Activity
import com.kar.enes.app.data.model.ArticleModel

/**
 * Created by M.Enes on 1/3/2020.
 */
interface NewsNavigator {

    fun getActivity(): Activity

    fun stopRefreshing()

    fun updateRv(list: ArrayList<ArticleModel>, exSize: Int)
}