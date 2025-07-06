package pe.edu.upc.vacapp.shared.data.local

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

object JwtStorage {
    private const val PREF_NAME = "prefs"
    private const val KEY_JWT = "jwt"
    private const val KEY_USER_ID = "user_id"
    private lateinit var prefs: SharedPreferences

    fun init(context: Context) {
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun saveToken(token: String) {
        prefs.edit { putString(KEY_JWT, token) }
    }

    fun getToken(): String? {
        return prefs.getString(KEY_JWT, null)
    }

    fun clearToken() {
        prefs.edit { remove(KEY_JWT) }
    }

    fun saveUserId(userId: Int) {
        prefs.edit { putInt(KEY_USER_ID, userId) }
    }

    fun getUserId(): Int? {
        val userId = prefs.getInt(KEY_USER_ID, -1)
        return if (userId != -1) userId else null
    }

    fun clearUserId() {
        prefs.edit { remove(KEY_USER_ID) }
    }
}
