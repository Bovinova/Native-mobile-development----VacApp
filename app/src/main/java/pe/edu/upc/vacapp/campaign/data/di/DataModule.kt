package pe.edu.upc.vacapp.campaign.data.di

import pe.edu.upc.vacapp.barn.data.di.DataModule.getBarnDao
import pe.edu.upc.vacapp.campaign.data.local.CampaignDao
import pe.edu.upc.vacapp.campaign.data.remote.CampaignService
import pe.edu.upc.vacapp.campaign.data.repository.CampaignRepository
import pe.edu.upc.vacapp.shared.data.di.SharedDataModule.getAppDatabase
import pe.edu.upc.vacapp.shared.data.di.SharedDataModule.getRetrofit

object DataModule {
    fun getCampaignRepository(): CampaignRepository {
        return CampaignRepository(getCampaingService(), getCampaingDao(), getBarnDao())
    }

    fun getCampaingDao(): CampaignDao {
        return getAppDatabase().campaignDao()
    }

    fun getCampaingService(): CampaignService {
        return getRetrofit().create(CampaignService::class.java)
    }
}