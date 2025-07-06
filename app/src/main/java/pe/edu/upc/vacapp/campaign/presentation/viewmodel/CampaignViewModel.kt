package pe.edu.upc.vacapp.campaign.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import pe.edu.upc.vacapp.barn.domain.model.Barn
import pe.edu.upc.vacapp.campaign.data.repository.CampaignRepository
import pe.edu.upc.vacapp.campaign.domain.model.Campaign

class CampaignViewModel(
    private val campaignRepository: CampaignRepository
) : ViewModel() {
    private val _campaigns = MutableStateFlow<List<Campaign>>(emptyList())
    val campaigns: StateFlow<List<Campaign>> = _campaigns

    private val _barns = MutableStateFlow<List<Barn>>(emptyList())
    val barn: StateFlow<List<Barn>> = _barns

    fun addCanpaing(campaign: Campaign) {
        viewModelScope.launch {
            campaignRepository.addCampaign(campaign)
        }
    }

    fun getCampaing() {
        viewModelScope.launch {

            _campaigns.value = campaignRepository.getCampaign()

        }
    }

    fun getBarns() {
        viewModelScope.launch {
            _barns.value = campaignRepository.getBarns()
        }
    }
}
