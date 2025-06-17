package pe.edu.upc.vacapp.barn.data.remote

import pe.edu.upc.vacapp.barn.data.model.BarnResponse
import pe.edu.upc.vacapp.barn.data.model.CreateBarnRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface BarnService {
    @POST("stables")
    suspend fun createBarn(
        @Body
        barn: CreateBarnRequest
    ): Response<BarnResponse>

    @GET("stables")
    suspend fun getBarns(): Response<List<BarnResponse>>
}