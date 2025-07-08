package pe.edu.upc.vacapp.shared

import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.google.gson.Gson
import org.json.JSONObject
import pe.edu.upc.vacapp.Vacapp
import pe.edu.upc.vacapp.shared.data.model.PendingOperationEntity

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

fun createPendingOperation(type: String = "POST", entity: String, data: Any, localId: Int): PendingOperationEntity {
    val json = Gson().toJson(data)
    return PendingOperationEntity(type = type, entity = entity, dataJson = json, localId = localId)
}
