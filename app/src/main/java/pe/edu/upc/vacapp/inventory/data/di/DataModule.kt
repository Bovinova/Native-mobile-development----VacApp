package pe.edu.upc.vacapp.inventory.data.di

import pe.edu.upc.vacapp.animal.data.di.DataModule.getAnimalRepository
import pe.edu.upc.vacapp.animal.data.local.AnimalDao
import pe.edu.upc.vacapp.home.data.di.DataModule.getUserInfoDao
import pe.edu.upc.vacapp.inventory.data.local.InventoryDao
import pe.edu.upc.vacapp.inventory.data.remote.InventoryService
import pe.edu.upc.vacapp.inventory.data.repository.InventoryRepository
import pe.edu.upc.vacapp.shared.data.di.SharedDataModule.getAppDatabase
import pe.edu.upc.vacapp.shared.data.di.SharedDataModule.getPendingOperationDao
import pe.edu.upc.vacapp.shared.data.di.SharedDataModule.getRetrofit

object DataModule {
    fun getInventoryService(): InventoryService {
        return getRetrofit().create(InventoryService::class.java)
    }

    fun getInventoryDao(): InventoryDao {
        return getAppDatabase().inventoryDao()
    }

    fun getAnimalDao(): AnimalDao {
        return getAppDatabase().animalDao()
    }

    fun getInventoryRepository(): InventoryRepository {
        return InventoryRepository(
            getInventoryService(),
            getInventoryDao(),
            getAnimalDao(),
            getPendingOperationDao(),
            getAnimalRepository(),
            getUserInfoDao()
        )
    }
}