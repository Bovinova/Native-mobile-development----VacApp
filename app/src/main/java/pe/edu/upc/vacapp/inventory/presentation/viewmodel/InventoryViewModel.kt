package pe.edu.upc.vacapp.inventory.presentation.viewmodel

import android.os.Build
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import pe.edu.upc.vacapp.inventory.data.repository.InventoryRepository
import pe.edu.upc.vacapp.inventory.domain.model.Inventory
import pe.edu.upc.vacapp.animal.domain.model.Animal

class InventoryViewModel(
    private val inventoryRepository: InventoryRepository
) : ViewModel() {
    private val _inventories = MutableStateFlow<List<Inventory>>(emptyList())
    val inventories: StateFlow<List<Inventory>> = _inventories

    private val _animals = MutableStateFlow<List<Animal>>(emptyList())
    val animal: StateFlow<List<Animal>> = _animals

    fun addInventory(inventory: Inventory) {
        viewModelScope.launch {
            inventoryRepository.addInventory(inventory)
        }
    }

    fun getAllInventories() {
        viewModelScope.launch {
            _inventories.value = inventoryRepository.getAllInventories()
        }
    }

    fun getAnimals() {
        viewModelScope.launch {
            _animals.value = inventoryRepository.getAnimals()
        }
    }
}