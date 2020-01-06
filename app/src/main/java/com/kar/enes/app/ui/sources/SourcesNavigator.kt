package com.kar.enes.app.ui.sources

import android.app.Activity

/**
 * Created by M.Enes on 1/2/2020.
 */
interface SourcesNavigator {

    fun getActivity(): Activity

    fun stopRefreshing()
}