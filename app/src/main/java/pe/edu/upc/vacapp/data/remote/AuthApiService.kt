package pe.edu.upc.vacapp.data.remote

import pe.edu.upc.vacapp.data.model.LoginRequest
import pe.edu.upc.vacapp.data.model.RegisterRequest
import pe.edu.upc.vacapp.data.model.UserResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {
    @POST("user/sign-in")
    suspend fun login(@Body request: LoginRequest): UserResponse

    @POST("user/sign-up")
    suspend fun register(@Body request: RegisterRequest): UserResponse


}
