package pe.edu.upc.vacapp.campaign.data.di

import pe.edu.upc.vacapp.barn.data.di.DataModule.getBarnDao
import pe.edu.upc.vacapp.campaign.data.local.CampaignDao
import pe.edu.upc.vacapp.campaign.data.remote.CampaignService
import pe.edu.upc.vacapp.campaign.data.repository.CampaignRepository
import pe.edu.upc.vacapp.shared.data.di.SharedDataModule.getAppDatabase
import pe.edu.upc.vacapp.shared.data.di.SharedDataModule.getPendingOperationDao
import pe.edu.upc.vacapp.shared.data.di.SharedDataModule.getRetrofit

object DataModule {
    fun getCampaignRepository(): CampaignRepository {
        return CampaignRepository(
            getCampaignService(),
            getCampaignDao(),
            getBarnDao(),
            getPendingOperationDao()
        )
    }

    fun getCampaignDao(): CampaignDao {
        return getAppDatabase().campaignDao()
    }

    fun getCampaignService(): CampaignService {
        return getRetrofit().create(CampaignService::class.java)
    }
}