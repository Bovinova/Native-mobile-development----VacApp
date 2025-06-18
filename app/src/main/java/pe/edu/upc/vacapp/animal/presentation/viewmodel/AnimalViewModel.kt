package pe.edu.upc.vacapp.animal.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import pe.edu.upc.vacapp.animal.data.repository.AnimalRepository
import pe.edu.upc.vacapp.animal.domain.model.Animal
import pe.edu.upc.vacapp.barn.domain.model.Barn

class AnimalViewModel(
    private val animalRepository: AnimalRepository
) : ViewModel() {
    private val _animals = MutableStateFlow<List<Animal>>(emptyList())
    val animals: StateFlow<List<Animal>> = _animals

    private val _barns = MutableStateFlow<List<Barn>>(emptyList())
    val barn: StateFlow<List<Barn>> = _barns


    fun addAnimal(animal: Animal) {
        viewModelScope.launch {
            animalRepository.addAnimal(animal)
        }
    }

    fun getAllAnimals() {
        viewModelScope.launch {
            _animals.value = animalRepository.getAllAnimals()
        }
    }

    fun getBarns() {
        viewModelScope.launch {
            _barns.value = animalRepository.getBarns()
        }
    }
}