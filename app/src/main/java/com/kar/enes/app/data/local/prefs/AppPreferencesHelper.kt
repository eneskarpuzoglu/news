package com.kar.enes.app.data.local.prefs

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.google.gson.Gson
import com.google.gson.JsonParser
import com.kar.enes.app.di.annotations.PreferenceInfo
import com.kar.enes.app.utils.AppConstants
import java.lang.reflect.InvocationTargetException
import javax.inject.Inject

/**
 * Created by M.Enes on 5/9/2019
 */
class AppPreferencesHelper @Inject constructor(val context: Context, @PreferenceInfo val prefFileName: String): PreferencesHelper {

    val mPrefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    override fun getReadList(): ArrayList<String>? {
        return getComplex(AppConstants.PREF_KEY_READ_LIST,ArrayList<String>()::class.java)
    }

    override fun setReadList(list: ArrayList<String>) {
        saveComplex(AppConstants.PREF_KEY_READ_LIST,list)
    }


    fun <T> saveComplex(key: String, complex: T): Boolean {
        val editor = mPrefs.edit()
        editor.putString(key,  Gson().toJson(complex))
        return editor.commit()
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> getComplex(key: String, type: Class<T>): T? {
        val data = mPrefs.getString(key, null)
        if (data != null && data != "null") {
            val mJson = JsonParser().parse(data)
            return Gson().fromJson<T>(mJson, type)
        }
        val ctor = type.constructors
        try {
            return ctor[0].newInstance() as T
        } catch (e: InstantiationException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: InvocationTargetException) {
            e.printStackTrace()
        }

        return null
    }
}