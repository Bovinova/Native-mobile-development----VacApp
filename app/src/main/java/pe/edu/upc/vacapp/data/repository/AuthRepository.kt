package pe.edu.upc.vacapp.data.repository

import pe.edu.upc.vacapp.data.model.LoginRequest
import pe.edu.upc.vacapp.data.model.RegisterRequest
import pe.edu.upc.vacapp.data.model.UserResponse
import pe.edu.upc.vacapp.data.remote.AuthApiService


class AuthRepository(private val apiService: AuthApiService) {

    suspend fun login(email: String, password: String): UserResponse {
        return apiService.login(LoginRequest(email, password))
    }


    suspend fun register(username: String, password: String,email: String): UserResponse {
        return apiService.register(RegisterRequest(username, password,email))
    }
}
