package pe.edu.upc.vacapp.data.remote

import pe.edu.upc.vacapp.data.model.BovineResponse
import retrofit2.http.GET
import retrofit2.Response


interface BovineService {
    @GET("bovine")
    suspend fun searchBovine(): Response<List<BovineResponse>>
}