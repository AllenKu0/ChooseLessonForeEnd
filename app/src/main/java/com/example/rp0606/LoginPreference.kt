package com.example.rp0606

import android.app.Application
import android.content.Context
import java.util.*

class LoginPreference(override val context: Context):PreferenceHelper(context) {
    companion object{
        private const val ACCOUNT = "ACCOUNT"
        private const val PASSWORD = "PASSWORD"
        private const val REMEMBER = "REMEMBER"
        private const val NAME = "NAME"
        private const val PHONE = "PHONE"
    }
    fun saveAccount(account:String){
        save(PreferenceHelper.Companion.Type.STRING, ACCOUNT,account as Object)
    }
    fun getAccount():String{
        return get(PreferenceHelper.Companion.Type.STRING,ACCOUNT) as String
    }

    fun savaPassWord(password:String){
        save(PreferenceHelper.Companion.Type.STRING, PASSWORD,password as Object)
    }

    fun getPassWord():String{
        return get(PreferenceHelper.Companion.Type.STRING,PASSWORD) as String
    }

    fun saveRememberStatus(isCheck:Boolean){
        save(PreferenceHelper.Companion.Type.BOOLEAN, REMEMBER,isCheck as Object)
    }

    fun getRememberStatus():Boolean{
        return get(PreferenceHelper.Companion.Type.BOOLEAN,REMEMBER) as Boolean
    }
}