package pe.edu.upc.vacapp.presentation.viewmodel

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

    init {
        loadBovines()
    }

    private fun loadBovines() {
        viewModelScope.launch {
            _bovines.value = bovineRepository.searchBovine()
        }
    }

    /* fun insertBovine(bovine: Bovine) {
        viewModelScope.launch {
            bovineRepository.insertBovine(bovine)
            loadBovines()
        }
    }

    fun deleteBovine(bovine: Bovine) {
        viewModelScope.launch {
            bovineRepository.deleteBovine(bovine)
            loadBovines() // recarga lista tras eliminar
        }
    }
    fun updateBovine(bovine: Bovine) {
        viewModelScope.launch {
            bovineRepository.updateBovine(bovine)
            loadBovines() // recarga lista tras actualizar
        }
     */

}