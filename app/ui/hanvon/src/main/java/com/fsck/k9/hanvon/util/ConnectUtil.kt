package com.fsck.k9.hanvon.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest


object ConnectUtil {
    var isHaveNet = true
    private lateinit var connectivityManager: ConnectivityManager
    fun init(context: Context) {
        connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val networkRequest = NetworkRequest.Builder()
            .build()
        connectivityManager.registerNetworkCallback(networkRequest, object : NetworkCallback() {
            override fun onAvailable(network: Network) {
                // 网络连接可用
                isHaveNet = true
            }

            override fun onLost(network: Network) {
                // 网络连接丢失
                isHaveNet = false
            }

            override fun onCapabilitiesChanged(
                network: Network,
                networkCapabilities: NetworkCapabilities
            ) {
                // 网络连接能力发生变化
            }
        })
    }
}
