package com.example.mistaketracker.utility

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.util.Log
import android.widget.Toast
import com.example.mistaketracker.Services.DataSyncService

class NetworkChangeReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        Log.d("s23","BR")

        // Obtain the ConnectivityManager system service from the context
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        // Retrieve the active network information
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo

        // Check if the active network is connected
        if (activeNetwork?.isConnected == true) {
            val serviceIntent = Intent(context, DataSyncService::class.java)
            context.startService(serviceIntent)
//           startService(Intent(this, DataSyncService::class.java))
//            Log.d("s23","Connect")
//
//            Toast.makeText(context, "Connect ed to Internet", Toast.LENGTH_SHORT).show()
            // Perform additional actions when connected to the Internet (e.g., enabling UI elements)
        } else {
            // Network is disconnected
            // Show a Toast message indicating that the device is not connected to the internet
            Toast.makeText(context, "Disconnected from Internet", Toast.LENGTH_SHORT).show()
            // Perform additional actions when not connected to the Internet (e.g., disabling UI elements)
        }
    }

}
