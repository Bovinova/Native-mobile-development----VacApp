package pe.edu.upc.vacapp.inventory.data.remote

import okhttp3.MultipartBody
import okhttp3.RequestBody
import pe.edu.upc.vacapp.animal.data.model.AnimalResponse
import pe.edu.upc.vacapp.inventory.data.model.InventoryResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface InventoryService {
    /*
    string Name,
    string VaccineType,
    DateTime VaccineDate,
    int BovineId,
    IFormFile? FileData;*/

    @Multipart
    @POST("vaccines")
    suspend fun addInventory(
        @Part("Name") name: RequestBody,
        @Part("VaccineType") vaccineType: RequestBody,
        @Part("VaccineDate") vaccineDate: RequestBody,
        @Part("BovineId") bovineId: RequestBody,
        @Part FileData: MultipartBody.Part
    ): Response<InventoryResponse>

    @GET("vaccines")
    suspend fun getAllInventories(): Response<List<InventoryResponse>>

    @GET("bovines")
    suspend fun getAnimals(): Response<List<AnimalResponse>>
}