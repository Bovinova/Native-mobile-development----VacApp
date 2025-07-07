package pe.edu.upc.vacapp.inventory.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import pe.edu.upc.vacapp.Vacapp
import pe.edu.upc.vacapp.animal.data.local.AnimalDao
import pe.edu.upc.vacapp.animal.domain.model.Animal
import pe.edu.upc.vacapp.inventory.data.local.InventoryDao
import pe.edu.upc.vacapp.inventory.data.model.AddInventoryRequest
import pe.edu.upc.vacapp.inventory.data.model.InventoryEntity
import pe.edu.upc.vacapp.inventory.data.model.toMultipartPart
import pe.edu.upc.vacapp.inventory.data.model.toRequestBody
import pe.edu.upc.vacapp.inventory.data.remote.InventoryService
import pe.edu.upc.vacapp.inventory.domain.model.Inventory
import pe.edu.upc.vacapp.shared.data.local.JwtStorage
import pe.edu.upc.vacapp.shared.isOnline
import java.io.File
import java.net.URL

class InventoryRepository(
    private val inventoryService: InventoryService,
    private val inventoryDao: InventoryDao,
    private val animalDao: AnimalDao
) {

    suspend fun addInventory(inventory: Inventory) = withContext(Dispatchers.IO) {
        val userId = JwtStorage.getUserId() ?: throw Exception("User not authenticated")
        val inventoryEntity = InventoryEntity.fromInventory(inventory, userId)
        inventoryDao.insertInventory(inventoryEntity)

        if (isOnline()) {
            try {
                val req = AddInventoryRequest.fromInventory(inventory)
                val res = inventoryService.addInventory(
                    req.name.toRequestBody(),
                    req.vaccineType.toRequestBody(),
                    req.vaccineDate.toRequestBody(),
                    req.bovineId.toRequestBody(),
                    req.image.toMultipartPart("FileData")
                )

                if (res.isSuccessful) {
                    inventoryDao.updateSyncedStatus(inventory.id!!, true)
                } else {
                    throw Exception("Error adding inventory: ${res.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                throw Exception("Error de red: ${e.message}")
            }
        }
    }

    suspend fun getAllInventories(): List<Inventory> = withContext(Dispatchers.IO) {
        val userId = JwtStorage.getUserId() ?: throw Exception("User not authenticated")
        val localInventories = inventoryDao.getInventoriesByUserId(userId).map { it.toInventory() }

        if (isOnline()) {
            val response = inventoryService.getAllInventories()

            if (response.isSuccessful) {
                val inventoriesFromApi = response.body()?.map { it.toInventoryEntity(userId) } ?: emptyList()

                inventoriesFromApi.forEach { inventoryEntity ->
                    val localPath = downloadImageToInternalStorage(inventoryEntity.imagePath, inventoryEntity.id)

                    val existingInventory = inventoryDao.getInventoryById(inventoryEntity.id)
                    if (existingInventory == null) {
                        val inventoryWithUpdatedPath = inventoryEntity.copy(imagePath = localPath ?: inventoryEntity.imagePath)
                        inventoryDao.insertInventory(inventoryWithUpdatedPath)
                    } else {
                        if (localPath != null) {
                            inventoryDao.updateSyncedStatus(inventoryEntity.id, true)
                        }
                    }
                }

                return@withContext inventoryDao.getInventoriesByUserId(userId).map { it.toInventory() }
            }
        }

        return@withContext localInventories
    }

    suspend fun downloadImageToInternalStorage(url: String, inventoryId: Int): String? = withContext(Dispatchers.IO) {
        val context = Vacapp.instance.applicationContext
        val destFile = File(context.filesDir, "inventory_$inventoryId.jpg")

        return@withContext try {
            val input = URL(url).openStream()
            input.use { inputStream ->
                destFile.outputStream().use { outputStream ->
                    inputStream.copyTo(outputStream)
                }
            }
            destFile.absolutePath
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun getAnimals(): List<Animal> = withContext(Dispatchers.IO) {
        val userId = JwtStorage.getUserId() ?: throw Exception("User not authenticated")
        val localAnimals = animalDao.getAnimalsByUserId(userId).map { it.toAnimal() }

        if (isOnline()) {
            val response = inventoryService.getAnimals()

            if (response.isSuccessful) {
                val animalsFromApi = response.body()?.map { it.toAnimalEntity(userId) } ?: emptyList()

                animalsFromApi.forEach { animalDao.insertAnimal(it) }

                return@withContext animalsFromApi.map { it.toAnimal() }
            }
        }

        return@withContext localAnimals
    }
}
