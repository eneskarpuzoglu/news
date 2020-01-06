package com.kar.enes.app.data.local.prefs

import com.kar.enes.app.data.DataManager

/**
 * Created by M.Enes on 5/9/2019
 */
interface PreferencesHelper {

    fun getReadList(): ArrayList<String>?

    fun setReadList(list: ArrayList<String>)
}