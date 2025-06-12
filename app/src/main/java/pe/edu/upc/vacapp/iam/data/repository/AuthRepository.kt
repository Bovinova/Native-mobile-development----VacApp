package pe.edu.upc.vacapp.iam.data.repository

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import pe.edu.upc.vacapp.iam.data.local.TokenDao
import pe.edu.upc.vacapp.iam.data.model.LoginRequest
import pe.edu.upc.vacapp.iam.data.model.RegisterRequest
import pe.edu.upc.vacapp.iam.data.remote.AuthService
import pe.edu.upc.vacapp.iam.domain.model.User
import pe.edu.upc.vacapp.shared.data.di.SharedDataModule

class AuthRepository(
    private val authService: AuthService,
    private val tokenDao: TokenDao
) {
    suspend fun login(user: User): Boolean = withContext(Dispatchers.IO) {
        val res = authService.login(LoginRequest.fromUser(user))

        if (!res.isSuccessful) return@withContext false

        val tokenEntity = res.body()?.toTokenEntity()

        if (tokenEntity != null) {
            tokenDao.saveToken(tokenEntity)
            return@withContext true
        }

        return@withContext false
    }

    suspend fun register(user: User): Boolean = withContext(Dispatchers.IO) {
        val res = authService.register(RegisterRequest.fromUser(user))

        if (!res.isSuccessful) return@withContext false

        val tokenEntity = res.body()?.toTokenEntity()

        if (tokenEntity != null) {
            tokenDao.saveToken(tokenEntity)
            return@withContext true
        }

        return@withContext false
    }

    suspend fun isLoggedIn(): Boolean = withContext(Dispatchers.IO) {
        val tokenEntity = tokenDao.getToken()
        return@withContext tokenEntity != null
    }

    suspend fun logout(): Boolean = withContext(Dispatchers.IO) {
        val tokenEntity = tokenDao.getToken()
        if (tokenEntity != null) {
            tokenDao.deleteToken(tokenEntity)
        }
        return@withContext true
    }
}