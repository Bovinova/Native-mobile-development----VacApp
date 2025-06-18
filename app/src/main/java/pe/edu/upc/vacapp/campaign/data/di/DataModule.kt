package pe.edu.upc.vacapp.campaign.data.di

import pe.edu.upc.vacapp.campaign.data.remote.CampaignService
import pe.edu.upc.vacapp.campaign.data.repository.CampaingRepository
import pe.edu.upc.vacapp.shared.data.di.SharedDataModule.getRetrofit

object DataModule {
    fun getCampaingRepository(): CampaingRepository {
        return CampaingRepository(getCampaingService())
    }

    fun getCampaingService(): CampaignService {
        return getRetrofit().create(CampaignService::class.java)
    }
}