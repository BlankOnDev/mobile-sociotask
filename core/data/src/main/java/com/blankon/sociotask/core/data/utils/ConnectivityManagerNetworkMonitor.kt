package com.blankon.sociotask.core.data.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkCapabilities
import androidx.core.content.getSystemService
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ConnectivityManagerNetworkMonitor @Inject constructor(
    @ApplicationContext private val context: Context,
) : NetworkMonitor {
    private val connectivityManager: ConnectivityManager? =
        context.getSystemService<ConnectivityManager>()

    override val isOnline: Flow<Boolean>
        get() = callbackFlow {
            val cm = connectivityManager
            if (cm == null) {
                trySend(false)
                close()
                return@callbackFlow
            }

            fun currentValidated(): Boolean {
                val net = cm.activeNetwork ?: return false
                val caps = cm.getNetworkCapabilities(net) ?: return false
                return caps.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
            }
            trySend(currentValidated())

            val callback = object : NetworkCallback() {
                override fun onCapabilitiesChanged(
                    network: Network,
                    networkCapabilities: NetworkCapabilities
                ) {
                    super.onCapabilitiesChanged(network, networkCapabilities)
                    trySend(
                        networkCapabilities.hasCapability(
                            NetworkCapabilities.NET_CAPABILITY_VALIDATED
                        )
                    )
                }

                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                    trySend(true)
                }

                override fun onLost(network: Network) {
                    super.onLost(network)
                    trySend(false)
                }

                override fun onUnavailable() {
                    super.onUnavailable()
                    trySend(false)
                }
            }
            cm.registerDefaultNetworkCallback(callback)
            awaitClose {
                cm.unregisterNetworkCallback(callback)
            }
        }.distinctUntilChanged()
}
