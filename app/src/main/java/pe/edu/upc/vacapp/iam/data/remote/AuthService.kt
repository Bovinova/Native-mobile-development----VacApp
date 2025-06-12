package pe.edu.upc.vacapp.iam.data.remote

import pe.edu.upc.vacapp.iam.data.model.LoginRequest
import pe.edu.upc.vacapp.iam.data.model.LoginResponse
import pe.edu.upc.vacapp.iam.data.model.RegisterRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthService {
    @POST("user/sign-in")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @POST("user/sign-up")
    suspend fun register(@Body request: RegisterRequest): Response<LoginResponse>
}