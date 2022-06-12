package com.example.rp0606

import android.app.Application
import android.content.Context
import java.util.*

class LoginPreference(override val context: Context):PreferenceHelper(context) {
    companion object{
        private const val ACCOUNT = "ACCOUNT"
        private const val NAME = "NAME"
        private const val PHONE = "PHONE"
    }
    fun saveAccount(account:String){
        save(PreferenceHelper.Companion.Type.STRING, ACCOUNT,account as Object)
    }
    fun getAccount():String{
        return get(PreferenceHelper.Companion.Type.STRING,ACCOUNT) as String
    }
}