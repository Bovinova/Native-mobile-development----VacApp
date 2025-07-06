package pe.edu.upc.vacapp.campaign.data.repository

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import pe.edu.upc.vacapp.barn.data.local.BarnDao
import pe.edu.upc.vacapp.barn.domain.model.Barn
import pe.edu.upc.vacapp.campaign.data.local.CampaignDao
import pe.edu.upc.vacapp.campaign.data.model.CampaignEntity
import pe.edu.upc.vacapp.campaign.data.model.CreateCampaignRequest
import pe.edu.upc.vacapp.campaign.data.remote.CampaignService
import pe.edu.upc.vacapp.campaign.domain.model.Campaign
import pe.edu.upc.vacapp.shared.data.local.JwtStorage
import pe.edu.upc.vacapp.shared.isOnline

class CampaignRepository(
    private val campaignService: CampaignService,
    private val campaignDao: CampaignDao,
    private val barnDao: BarnDao
) {
    suspend fun addCampaign(campaign: Campaign) = withContext(Dispatchers.IO) {
        val userId = JwtStorage.getUserId()

        if (userId == null) {
            throw Exception("User not authenticated")
        }

        val generatedId = (System.currentTimeMillis() % Int.MAX_VALUE).toInt()

        val campaignEntity = CampaignEntity.fromCampaign(campaign, userId, generatedId)

        campaignDao.insert(campaignEntity)

        if (isOnline()) {
            try {
                val data = CreateCampaignRequest.fromCampaign(campaign)
                val response = campaignService.createCampaign(data)

                if (response.isSuccessful) {
                    campaignDao.updateSyncedStatus(generatedId, true)
                }
            } catch (e: Exception) {
                //TODO
            }
        }
    }

    suspend fun getCampaign(): List<Campaign> = withContext(Dispatchers.IO) {
        val userId = JwtStorage.getUserId()

        if (userId == null) {
            throw Exception("User not authenticated")
        }

        val localCampaigns = campaignDao.getCampaignsByUserId(userId).map { it.toCampaign() }

        if (isOnline()) {
            val response = campaignService.getCampaign()

            if (response.isSuccessful) {
                val campaignsFromApi =
                    response.body()?.map { it.toCampaignEntity(userId) } ?: emptyList()

                campaignsFromApi.forEach { campaignDao.insert(it) }

                return@withContext campaignsFromApi.map { it.toCampaign() }
            }
        }

        return@withContext localCampaigns
    }

    suspend fun getBarns(): List<Barn> = withContext(Dispatchers.IO) {
        val userId = JwtStorage.getUserId()

        if (userId == null) {
            throw Exception("User not authenticated")
        }

        val localBarns = barnDao.getBarnsByUserId(userId).map { it.toBarn() }

        if (isOnline()) {
            val response = campaignService.getBarns()

            if (response.isSuccessful) {
                val barnsFromApi = response.body()?.map { it.toBarnEntity(userId) } ?: emptyList()

                barnsFromApi.forEach { barnDao.insert(it) }

                return@withContext barnsFromApi.map { it.toBarn() }
            }
        }

        return@withContext localBarns
    }
}