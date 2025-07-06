package pe.edu.upc.vacapp.shared

import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import org.json.JSONObject
import pe.edu.upc.vacapp.Vacapp

fun extractErrorMessage(json: String?): String {
    if (json.isNullOrEmpty()) return "Unknown error"
    return try {
        JSONObject(json).getString("error")
    } catch (e: Exception) {
        "Unknown error"
    }
}

fun isOnline(): Boolean {
    val connectivityManager = Vacapp.instance.getSystemService(ConnectivityManager::class.java)
    val network = connectivityManager.activeNetwork ?: return false
    val networkCapabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
    return networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
}