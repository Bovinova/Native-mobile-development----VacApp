package pe.edu.upc.vacapp.home.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import pe.edu.upc.vacapp.home.data.remote.UserInfoService
import pe.edu.upc.vacapp.home.domain.model.UserInfo

class UserInfoRepository(
    private val userInfoService: UserInfoService
) {
    suspend fun getUserInfo(): UserInfo = withContext(Dispatchers.IO) {
        val res = userInfoService.getUserInfo()

        if (res.isSuccessful) {
            return@withContext res.body()?.toUserInfo()
                ?: throw Exception("Empty response body")
        }

        throw Exception("Error fetching user info: ${res.errorBody()?.string() ?: "Unknown error"}")
    }
}