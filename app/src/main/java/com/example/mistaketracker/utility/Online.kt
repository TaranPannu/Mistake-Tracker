package com.example.mistaketracker.utility

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

object Online {
    fun isOnline(context: Context): Boolean {

        val connMgr = (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)
        val networkInfo: NetworkInfo? = connMgr.activeNetworkInfo
        return networkInfo?.isConnected == true
    }
}
