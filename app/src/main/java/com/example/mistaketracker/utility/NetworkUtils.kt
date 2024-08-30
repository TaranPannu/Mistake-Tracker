package com.example.mistaketracker.utility

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

// Extension function to create a Flow that monitors network connectivity
//fun Context.networkConnectivityFlow(): Flow<Boolean> = callbackFlow {
//    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//
//    val networkCallback = object : ConnectivityManager.NetworkCallback() {
//        override fun onAvailable(network: Network) {
//            trySend(true) // Emit true when network is available
//        }
//
//        override fun onLost(network: Network) {
//            trySend(false) // Emit false when network is lost
//        }
//    }
//
//    val networkRequest = NetworkRequest.Builder()
//        .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
//        .build()
//
//    connectivityManager.registerNetworkCallback(networkRequest, networkCallback)
//
//    // Remove callback when flow is closed
//    awaitClose {
//        connectivityManager.unregisterNetworkCallback(networkCallback)
//    }
//}

// Collect the network connectivity flow
//        networkConnectivityFlow()
//            .onEach { isConnected ->
//                // Handle network connectivity status
//                if (isConnected) {
//                    Toast.makeText(
//                        this@MainActivity,
//                        "Connected to Internet",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                    // Perform actions when connected to the Internet
//                    // For example: Show a Toast, Enable UI elements, etc.
//                } else {
//                    Toast.makeText(
//                        this@MainActivity,
//                        "Connected to Internet",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                    // Perform actions when not connected to the Internet
//                    // For example: Show a Toast, Disable UI elements, etc.
//                }
//            }
//            .launchIn(lifecycleScope) // Launch the flow collection in the lifecycleScope