package pe.edu.upc.vacapp.campaign.presentation.di

import pe.edu.upc.vacapp.campaign.data.di.DataModule.getCampaingRepository
import pe.edu.upc.vacapp.campaign.presentation.viewmodel.CampaignViewModel

object PresentacionModel {
    fun getCampaignViewModel(): CampaignViewModel{
        return CampaignViewModel(getCampaingRepository())
    }

}

