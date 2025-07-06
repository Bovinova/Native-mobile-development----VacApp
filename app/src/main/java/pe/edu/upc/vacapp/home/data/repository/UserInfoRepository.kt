package pe.edu.upc.vacapp.home.data.repository

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.threeten.bp.LocalDateTime
import pe.edu.upc.vacapp.home.data.local.NextCampaignDao
import pe.edu.upc.vacapp.home.data.local.UserInfoDao
import pe.edu.upc.vacapp.home.data.model.NextCampaignEntity
import pe.edu.upc.vacapp.home.data.model.UserInfoEntity
import pe.edu.upc.vacapp.home.data.remote.UserInfoService
import pe.edu.upc.vacapp.home.domain.model.UserInfo
import pe.edu.upc.vacapp.shared.data.local.JwtStorage
import pe.edu.upc.vacapp.shared.isOnline
import java.io.IOException

sealed class Result<out T> {
    data class Success<T>(val data: T) : Result<T>()
    data class Error(val exception: Throwable) : Result<Nothing>()
}

class UserInfoRepository(
    private val userInfoService: UserInfoService,
    private val userInfoDao: UserInfoDao,
    private val nextCampaignDao: NextCampaignDao
) {
    suspend fun getUserInfo(): Result<UserInfo> = withContext(Dispatchers.IO) {
        val userId = JwtStorage.getUserId()

        if (userId == null) {
            if (isOnline()) {
                return@withContext fetchAndStoreUserInfo()
            } else {
                return@withContext Result.Error(IOException("No internet connection and no local data"))
            }
        }

        val currentDate = LocalDateTime.now().toString()
        val localUser = userInfoDao.getUserInfoByUserId(userId)
        val localNextCampaigns = localUser?.let {
            nextCampaignDao.getUpcomingCampaigns(it.id, currentDate).map { nc -> nc.toNextCampaign() }
        } ?: emptyList()

        if (isOnline()) {
            val apiResult = fetchAndStoreUserInfo()
            if (apiResult is Result.Success) return@withContext apiResult
        }

        return@withContext if (localUser != null) {
            Result.Success(localUser.toUserInfo(localNextCampaigns))
        } else {
            Result.Error(IOException("No local data and no internet connection"))
        }
    }

    private suspend fun fetchAndStoreUserInfo(): Result<UserInfo> {
        return try {
            val response = userInfoService.getUserInfo()

            if (response.isSuccessful) {
                response.body()?.toUserInfo()?.let { userInfo ->
                    JwtStorage.saveUserId(userInfo.id)

                    val userEntity = UserInfoEntity.fromUserInfo(userInfo, userInfo.id)
                    val nextCampaignEntities = userInfo.nextCampaigns.map { nc ->
                        NextCampaignEntity.fromNextCampaign(nc, userInfo.id)
                    }

                    userInfoDao.insertUserInfo(userEntity)
                    nextCampaignDao.insertAll(nextCampaignEntities)

                    return Result.Success(userInfo)
                }
                Result.Error(IOException("Empty Response"))
            } else {
                val errorBody = response.errorBody()?.string() ?: "Unknown Error"
                Log.e("UserInfoRepository", "Error en API: $errorBody")
                Result.Error(IOException("Server error: $errorBody"))
            }
        } catch (e: Exception) {
            Log.e("UserInfoRepository", "Excepción en getUserInfo", e)
            Result.Error(e)
        }
    }

    /*suspend fun getUserInfo(): Result<UserInfo> = withContext(Dispatchers.IO) {
        try {
            val res = userInfoService.getUserInfo()

            if (res.isSuccessful) {
                res.body()?.toUserInfo()?.let {
                    JwtStorage.saveUserId(it.id)
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
    }*/
}
