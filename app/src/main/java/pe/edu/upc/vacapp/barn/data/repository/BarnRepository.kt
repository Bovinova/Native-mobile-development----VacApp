package pe.edu.upc.vacapp.barn.data.repository

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import pe.edu.upc.vacapp.barn.data.model.CreateBarnRequest
import pe.edu.upc.vacapp.barn.data.remote.BarnService
import pe.edu.upc.vacapp.barn.domain.model.Barn
import pe.edu.upc.vacapp.campaign.data.model.CreateCampaignRequest

class BarnRepository(
    private val barnService: BarnService
) {
    suspend fun addBarn(
        barn: Barn
    ) = withContext(Dispatchers.IO) {
        val data = CreateBarnRequest.fromBarn(barn)
        val response =barnService.createBarn(data)
        if (response.isSuccessful) {
            Log.d("prueba", response.body().toString())
        }
    }
}