package pe.edu.upc.vacapp.iam.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import pe.edu.upc.vacapp.iam.data.model.LoginRequest
import pe.edu.upc.vacapp.iam.data.model.RegisterRequest
import pe.edu.upc.vacapp.iam.data.remote.AuthService
import pe.edu.upc.vacapp.iam.domain.model.User

class AuthRepository(
    private val authService: AuthService
) {
    suspend fun login(user: User): Boolean = withContext(Dispatchers.IO) {
        val res = authService.login(LoginRequest.fromUser(user))

        return@withContext res.isSuccessful and res.body()?.token.isNullOrEmpty().not()
    }

    suspend fun register(user: User): Boolean = withContext(Dispatchers.IO) {
        val res = authService.register(RegisterRequest.fromUser(user))

        return@withContext res.isSuccessful and res.body()?.token.isNullOrEmpty().not()
    }
}