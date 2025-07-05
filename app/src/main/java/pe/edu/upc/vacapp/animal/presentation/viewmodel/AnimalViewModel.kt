package pe.edu.upc.vacapp.animal.presentation.viewmodel

import android.util.Log
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

    companion object {
        private const val TAG = "AnimalViewModel"
    }

    /* Declaration */
    //
    private val _animals = MutableStateFlow<List<Animal>>(emptyList())
    val animals: StateFlow<List<Animal>> = _animals
    //
    private val _barns = MutableStateFlow<List<Barn>>(emptyList())
    val barn: StateFlow<List<Barn>> = _barns
    //
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage
    //
    private val _addAnimalSuccess = MutableStateFlow(false)
    val addAnimalSuccess: StateFlow<Boolean> = _addAnimalSuccess


    /* Methods */
    //
    fun clearErrorMessage() {
        _errorMessage.value = null
    }
    //
    fun addAnimal(animal: Animal) {
        viewModelScope.launch {
            try {
                Log.d(TAG, "Adding animal: $animal")
                animalRepository.addAnimal(animal)
                Log.d(TAG, "Animal added successfully")
                _addAnimalSuccess.value = true  // <-- ✅ Success
            } catch (e: IllegalArgumentException) {
                Log.e(TAG, "IllegalArgumentException when adding animal", e)
                _errorMessage.value = e.message ?: "Error adding animal"
            } catch (e: Exception) {
                Log.e(TAG, "Exception when adding animal", e)
                _errorMessage.value = "Unknown error when adding the animal: ${e.message}"
            }
        }
    }
    //
    fun clearAddAnimalSuccess() {
        _addAnimalSuccess.value = false
    }
    //
    fun getAllAnimals() {
        viewModelScope.launch {
            try {
                Log.d(TAG, "Getting all animals")
                _animals.value = animalRepository.getAllAnimals()
                Log.d(TAG, "Retrieved ${_animals.value.size} animals")
            } catch (e: Exception) {
                Log.e(TAG, "Error getting all animals", e)
                _errorMessage.value = "Error loading animals: ${e.message}"
            }
        }
    }
    //
    fun getBarns() {
        viewModelScope.launch {
            try {
                Log.d(TAG, "Getting barns")
                _barns.value = animalRepository.getBarns()
                Log.d(TAG, "Retrieved ${_barns.value.size} barns")
            } catch (e: Exception) {
                Log.e(TAG, "Error getting barns", e)
                _errorMessage.value = "Error loading barns: ${e.message}"
            }
        }
    }
}