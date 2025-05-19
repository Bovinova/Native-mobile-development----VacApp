package pe.edu.upc.vacapp.presentation.viewmodel

import androidx.browser.trusted.Token
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import pe.edu.upc.vacapp.data.repository.BovineRepository
import pe.edu.upc.vacapp.domain.model.Bovine

class BovineViewModel(private val bovineRepository: BovineRepository): ViewModel()  {

    private val _bovines = MutableStateFlow<List<Bovine>>(emptyList())
    val bovine: StateFlow<List<Bovine>> = _bovines


    fun loadBovines(token: String) {
        viewModelScope.launch {
            _bovines.value = bovineRepository.searchBovine(token)
        }
    }



}