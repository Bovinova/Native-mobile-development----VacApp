package pe.edu.upc.vacapp.barn.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import pe.edu.upc.vacapp.barn.data.repository.BarnRepository
import pe.edu.upc.vacapp.barn.domain.model.Barn

class BarnViewModel (
    private val barnRepository : BarnRepository
):ViewModel(){
    fun addBarn (barn: Barn){
        viewModelScope.launch {
            barnRepository.addBarn(barn)
        }
    }
}