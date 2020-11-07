package com.example.virtualclasses.local

import android.content.Context
import android.preference.PreferenceManager
import com.google.gson.Gson

object SharedPrefsUtils{
    fun <T> saveObject(
        key: String?,
        value: T,
        context: Context?
    ) {
        val sharedPref =
            PreferenceManager.getDefaultSharedPreferences(context)
        val gson = Gson()
        val json = gson.toJson(value)
        val editor = sharedPref.edit()
        editor.putString(key, json)
        editor.apply()
    }

    fun <T> getObject(key: String?, context: Context?, returnObject: Class<T>): T {
        val sharedPref =
            PreferenceManager.getDefaultSharedPreferences(context)
        val gson = Gson()
        val json: String? = sharedPref.getString(key, "")
        return gson.fromJson(json, returnObject)
    }
}