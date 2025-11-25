package com.example.tbcacademy.common

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkHelper @Inject constructor(private val context: Context) {

    fun isNetworkConnected(): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network: Network? = cm.activeNetwork
        val capabilities: NetworkCapabilities? = cm.getNetworkCapabilities(network)
        return capabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
    }

    fun registerNetworkCallback(
        onAvailable: () -> Unit,
        onLost: () -> Unit
    ) {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        cm.registerDefaultNetworkCallback(object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                onAvailable()
            }

            override fun onLost(network: Network) {
                onLost()
            }
        })
    }
}
