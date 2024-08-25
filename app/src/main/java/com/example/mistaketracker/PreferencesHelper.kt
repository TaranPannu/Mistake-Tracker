//package com.example.mistaketracker
//
//import android.content.Context
//import android.content.SharedPreferences
//import javax.inject.Inject
//
//class PreferencesHelper @Inject constructor(context: Context) {
//
//    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
//
//    fun getToken(): String? {
//        return sharedPreferences.getString("auth_token", null)
//    }
//
//    fun saveToken(token: String) {
//        sharedPreferences.edit().putString("auth_token", token).apply()
//    }
//}
