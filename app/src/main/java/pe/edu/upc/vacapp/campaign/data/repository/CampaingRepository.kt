package pe.edu.upc.vacapp.campaign.data.repository

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import pe.edu.upc.vacapp.barn.domain.model.Barn
import pe.edu.upc.vacapp.campaign.data.model.CreateCampaignRequest
import pe.edu.upc.vacapp.campaign.data.remote.CampaignService
import pe.edu.upc.vacapp.campaign.domain.model.Campaign

class CampaingRepository(
    private val campaignService: CampaignService
) {
    suspend fun addCampaing(
        campaing: Campaign
    ) = withContext(Dispatchers.IO) {
        val data = CreateCampaignRequest.fromCampaign(campaing)
        val response = campaignService.createCampaign(data)
        if (response.isSuccessful) {
            Log.d("prueba", response.body().toString())
        }
    }

    suspend fun getCampaing(): List<Campaign> = withContext(Dispatchers.IO) {
        val response = campaignService.getCampaign()

        if (response.isSuccessful) {
            Log.d("prueba", response.body().toString())

            return@withContext response.body()?.map {
                it.toCampaign()
            } ?: emptyList()
        }

        return@withContext emptyList()
    }

    suspend fun getBarns(): List<Barn> = withContext(Dispatchers.IO) {
        val response = campaignService.getBarns()

        if (response.isSuccessful) {
            return@withContext response.body()?.map {
                it.toBarn()
            } ?: emptyList()
        }

        return@withContext emptyList()
    }
}