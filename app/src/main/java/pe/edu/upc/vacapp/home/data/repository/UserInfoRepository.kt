package pe.edu.upc.vacapp.home.data.repository

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import pe.edu.upc.vacapp.home.data.remote.UserInfoService
import pe.edu.upc.vacapp.home.domain.model.UserInfo
import java.io.IOException

sealed class Result<out T> {
    data class Success<T>(val data: T): Result<T>()
    data class Error(val exception: Throwable): Result<Nothing>()
}

class UserInfoRepository(
    private val userInfoService: UserInfoService
) {
    suspend fun getUserInfo(): Result<UserInfo> = withContext(Dispatchers.IO) {
        try {
            val res = userInfoService.getUserInfo()

            if (res.isSuccessful) {
                res.body()?.toUserInfo()?.let {
                    return@withContext Result.Success(it)
                }
                return@withContext Result.Error(IOException("Empty Response"))
            } else {
                val error = res.errorBody()?.string() ?: "Unknown Error"
                Log.e("UserInfoRepository", "Error en API: $error")
                return@withContext Result.Error(IOException("Server Connection: $error"))
            }
        } catch (e: Exception) {
            Log.e("UserInfoRepository", "Excepción en getUserInfo", e)
            return@withContext Result.Error(e)
        }
    }
}
