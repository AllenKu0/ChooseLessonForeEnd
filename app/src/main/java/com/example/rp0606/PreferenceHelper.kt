package com.example.rp0606

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.example.rp0606.PreferenceHelper.Companion.Type.Companion.BOOLEAN
import com.example.rp0606.PreferenceHelper.Companion.Type.Companion.FLOAT
import com.example.rp0606.PreferenceHelper.Companion.Type.Companion.INT
import com.example.rp0606.PreferenceHelper.Companion.Type.Companion.LONG
import com.example.rp0606.PreferenceHelper.Companion.Type.Companion.STRING
import com.example.rp0606.PreferenceHelper.Companion.Type.Companion.STRING_SET
import java.util.*

abstract class PreferenceHelper(open val context: Context) {

    fun save(type: Type, name: String, data: Object) {
        val pref = context.getSharedPreferences("pref", Context.MODE_PRIVATE)
        var editor: SharedPreferences.Editor = pref.edit()
        when (type){
            STRING -> editor.putString(name,data as String)
            FLOAT -> editor.putFloat(name,data as Float)
            INT -> editor.putInt(name,data as Int)
            LONG -> editor.putLong(name,data as Long)
            BOOLEAN -> editor.putBoolean(name,data as Boolean)
        }
        editor.commit()
    }

    fun get(type: Type,name: String): Object {
        val pref = context.getSharedPreferences("pref", Context.MODE_PRIVATE)

        when(type){
            STRING -> return pref.getString(name,"") as Object
            FLOAT -> return pref.getFloat(name,0F) as Object
            INT -> return pref.getInt(name,0) as Object
            LONG -> return pref.getLong(name,0) as Object
            BOOLEAN -> return pref.getBoolean(name,false) as Object
            else -> throw RuntimeException("Must use base type(String, Float, Double, Integer, Long), type from input is ")
        }
    }

    companion object {
        class Type {
            companion object{
                val STRING: Type = Type()
                val FLOAT: Type = Type()
                val INT: Type = Type()
                val LONG: Type = Type()
                val BOOLEAN: Type = Type()
                val STRING_SET: Type = Type()
            }
        }
    }
}