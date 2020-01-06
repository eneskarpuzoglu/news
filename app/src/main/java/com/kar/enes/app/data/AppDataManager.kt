package com.kar.enes.app.data

import com.kar.enes.app.data.local.prefs.PreferencesHelper
import com.kar.enes.app.data.model.request.LoginReq
import com.kar.enes.app.data.remote.ApiHelper
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by M.Enes on 24.04.2019
 */
@Singleton
class AppDataManager @Inject constructor(private val api: ApiHelper,private val preferencesHelper: PreferencesHelper) : DataManager {

    override fun getReadList(): ArrayList<String>? = preferencesHelper.getReadList()

    override fun setReadList(list: ArrayList<String>) = preferencesHelper.setReadList(list)

    //-------------------

    override fun getSources(id: String) = api.getSources(id)

    override fun getTopHeadlines(id: String, sourceId: String) = api.getTopHeadlines(id,sourceId)
}