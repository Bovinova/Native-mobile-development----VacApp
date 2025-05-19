package pe.edu.upc.vacapp.data.remote

import pe.edu.upc.vacapp.data.model.BovineResponse
import retrofit2.http.GET
import retrofit2.Response
import retrofit2.http.Header


interface BovineService {
    @GET("bovines")
    suspend fun searchBovine(@Header("Authorization") token: String): Response<List<BovineResponse>>
}