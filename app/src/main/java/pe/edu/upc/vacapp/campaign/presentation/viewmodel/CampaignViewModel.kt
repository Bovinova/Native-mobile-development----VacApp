package pe.edu.upc.vacapp.campaign.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import pe.edu.upc.vacapp.campaign.data.repository.CampaingRepository
import pe.edu.upc.vacapp.campaign.domain.model.Campaign

class CampaignViewModel(
    private val campaingRepository: CampaingRepository
) : ViewModel() {
    private val _campaigns = MutableStateFlow<List<Campaign>>(emptyList())
    val campaigns: StateFlow<List<Campaign>> = _campaigns

    fun addCanpaing(campaign: Campaign) {
        viewModelScope.launch {
            campaingRepository.addCampaing(campaign)
        }
    }

    fun getCampaing() {
        viewModelScope.launch {

            _campaigns.value = campaingRepository.getCampaing()

        }
    }
}
