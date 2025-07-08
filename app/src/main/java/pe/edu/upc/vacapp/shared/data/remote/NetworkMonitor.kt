package pe.edu.upc.vacapp.shared.data.remote

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import pe.edu.upc.vacapp.Vacapp

object NetworkMonitor {
    private val connectivityManager = Vacapp.instance.getSystemService(ConnectivityManager::class.java)

    fun startMonitoring() {
        val request = NetworkRequest.Builder().build()

        connectivityManager.registerNetworkCallback(request, object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                // Cuando vuelve la conexión, lanzamos el worker
                launchSyncWorker()
            }
        })
    }

    private fun launchSyncWorker() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val syncRequest = OneTimeWorkRequestBuilder<SyncWorker>()
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(Vacapp.instance).enqueue(syncRequest)
    }
}
