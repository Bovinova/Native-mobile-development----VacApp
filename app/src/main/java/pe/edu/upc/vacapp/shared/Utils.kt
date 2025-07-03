package pe.edu.upc.vacapp.shared

import org.json.JSONObject

fun extractErrorMessage(json: String?): String {
    if (json.isNullOrEmpty()) return "Unknown error"
    return try {
        JSONObject(json).getString("error")
    } catch (e: Exception) {
        "Unknown error"
    }
}