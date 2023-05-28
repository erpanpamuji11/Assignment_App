package com.methe.assignmentapp.utils

import android.content.Context
import android.content.SharedPreferences

class PrefsHelper(context: Context) {

    private val PRIVATE_MODE = 0

    private val PREF_NAME = "AuthEmailPassword"
    private val IS_LOGIN = "is_login"

    private var prefs: SharedPreferences
    private var editor: SharedPreferences.Editor

    init {
        prefs = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
        editor = prefs.edit()
    }

    fun put(key: String, value: String){
        editor.putString(key, value)
            .apply()
    }

    fun getString(key: String): String? {
        return prefs.getString(key, null)
    }

    fun put(key: String, value: Boolean){
        editor.putBoolean(key, value)
            .apply()
    }

    fun getBoolean(key: String): Boolean {
        return prefs.getBoolean(key, false)
    }

    fun clear(){
        editor.clear()
            .apply()
    }

}