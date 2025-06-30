package pe.edu.upc.vacapp.inventory.presentation.di

import pe.edu.upc.vacapp.inventory.data.di.DataModule.getInventoryRepository
import pe.edu.upc.vacapp.inventory.presentation.viewmodel.InventoryViewModel

object PresentationModule {
    fun getInventoryViewModel(): InventoryViewModel {
        return InventoryViewModel(getInventoryRepository())
    }
}