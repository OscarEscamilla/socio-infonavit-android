package com.example.infonavit.utils

import android.content.Context
import android.content.SharedPreferences
import android.util.JsonToken

class UserPreferences(context: Context) {


    private val KEY_TOKEN = "auth"
    private var tokenStr: String = ""
    private val PREFERENCES_NAME = "socioinfonavit"
    private var sharedPreferences: SharedPreferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
    private var editor: SharedPreferences.Editor = sharedPreferences.edit()

    fun saveToken(token: String){

        editor.putString(KEY_TOKEN, token)
        editor.commit()

    }

    fun getToken(): String{

        if (sharedPreferences.contains(KEY_TOKEN)){
            tokenStr = sharedPreferences.getString(KEY_TOKEN,"").toString()
        }

        return tokenStr
    }


    fun updateToken(token: String){

    }

    fun deleteToken(){
        editor.remove(KEY_TOKEN)
        editor.commit()
    }
}