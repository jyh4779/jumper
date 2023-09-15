package kr.jyh.jumper.Shared

import android.content.Context
import android.content.SharedPreferences

class PreferenceUtil(context:Context) {
    private val pref:SharedPreferences = context.getSharedPreferences("JUMP_PREF",Context.MODE_PRIVATE)

    fun getString(key:String, defValue:String):String{
        return pref.getString(key,defValue).toString()
    }

    fun setString(key:String, defValue: String){
        pref.edit().putString(key,defValue).apply()
    }
}