package pe.edu.upc.vacapp.barn.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import pe.edu.upc.vacapp.barn.data.repository.BarnRepository
import pe.edu.upc.vacapp.barn.domain.model.Barn

class BarnViewModel(
    private val barnRepository: BarnRepository
) : ViewModel() {
    private val _barns = MutableStateFlow<List<Barn>>(emptyList())
    val barn: StateFlow<List<Barn>> = _barns

    fun addBarn(barn: Barn) {
        viewModelScope.launch {
            barnRepository.addBarn(barn)
        }
    }

    fun getBarns() {
        viewModelScope.launch {
            _barns.value = barnRepository.getBarns()
        }
    }
}