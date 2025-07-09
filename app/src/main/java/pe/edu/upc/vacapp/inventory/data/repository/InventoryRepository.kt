package pe.edu.upc.vacapp.inventory.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import pe.edu.upc.vacapp.Vacapp
import pe.edu.upc.vacapp.animal.data.local.AnimalDao
import pe.edu.upc.vacapp.animal.data.repository.AnimalRepository
import pe.edu.upc.vacapp.animal.domain.model.Animal
import pe.edu.upc.vacapp.home.data.local.UserInfoDao
import pe.edu.upc.vacapp.inventory.data.local.InventoryDao
import pe.edu.upc.vacapp.inventory.data.model.AddInventoryRequest
import pe.edu.upc.vacapp.inventory.data.model.InventoryEntity
import pe.edu.upc.vacapp.inventory.data.model.toMultipartPart
import pe.edu.upc.vacapp.inventory.data.model.toRequestBody
import pe.edu.upc.vacapp.inventory.data.remote.InventoryService
import pe.edu.upc.vacapp.inventory.domain.model.Inventory
import pe.edu.upc.vacapp.shared.createPendingOperation
import pe.edu.upc.vacapp.shared.data.local.JwtStorage
import pe.edu.upc.vacapp.shared.data.local.PendingOperationDao
import pe.edu.upc.vacapp.shared.isOnline
import java.io.File
import java.net.URL

class InventoryRepository(
    private val inventoryService: InventoryService,
    private val inventoryDao: InventoryDao,
    private val animalDao: AnimalDao,
    private val pendingOperationDao: PendingOperationDao,
    private val animalRepository: AnimalRepository,
    private val userInfoDao: UserInfoDao
) {
    suspend fun addInventory(inventory: Inventory) = withContext(Dispatchers.IO) {
        val userId = JwtStorage.getUserId() ?: throw Exception("User not authenticated")

        val lastId = inventoryDao.getLastId() ?: 0
        val generatedId = lastId + 1

        val finalImagePath = copyImageToInternalStorageIfNeeded(inventory.image)
        val updatedInventory = inventory.copy(image = finalImagePath)
        val inventoryEntity = InventoryEntity.fromInventory(updatedInventory, userId, generatedId)

        inventoryDao.insert(inventoryEntity)
        userInfoDao.increaseTotalVaccinations(userId)

        if (isOnline()) {
            try {
                val req = AddInventoryRequest.fromInventory(updatedInventory)
                val res = inventoryService.addInventory(
                    req.name.toRequestBody(),
                    req.vaccineType.toRequestBody(),
                    req.vaccineDate.toRequestBody(),
                    req.bovineId.toRequestBody(),
                    req.image.toMultipartPart("FileData")
                )

                if (res.isSuccessful) {
                    val inventoryFromApi = res.body()

                    if (inventoryFromApi != null) {
                        inventoryDao.deleteById(generatedId)

                        val newAnimalEntity = inventoryFromApi.toInventoryEntity(userId)
                        inventoryDao.insert(newAnimalEntity)
                    }

                    inventoryDao.updateSyncedStatus(inventory.id, true)
                    inventoryDao.deleteById(generatedId)
                } else {
                    throw Exception("Error adding inventory: ${res.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                throw Exception("Error de red: ${e.message}")
            }
        } else {
            val animal = animalDao.getAnimalById(inventoryEntity.bovineId)
                ?: throw Exception("Barn not found for campaign")

            val localId = if (!animal.synced) animal.id else 0

            val pendingOperation = createPendingOperation(
                entity = "inventory",
                data = inventoryEntity,
                localId = localId
            )

            pendingOperationDao.insert(pendingOperation)
        }
    }

    suspend fun getAllInventories(): List<Inventory> = withContext(Dispatchers.IO) {
        val userId = JwtStorage.getUserId() ?: throw Exception("User not authenticated")
        val localInventories = inventoryDao.getInventoriesByUserId(userId).map { it.toInventory() }

        if (isOnline()) {
            val response = inventoryService.getAllInventories()

            if (response.isSuccessful) {
                val inventoriesFromApi =
                    response.body()?.map { it.toInventoryEntity(userId) } ?: emptyList()

                inventoriesFromApi.forEach { inventoryEntity ->
                    val localPath =
                        downloadImageToInternalStorage(
                            inventoryEntity.imagePath,
                            inventoryEntity.id
                        )

                    val existingInventory = inventoryDao.getInventoryById(inventoryEntity.id)
                    if (existingInventory == null) {
                        val inventoryWithUpdatedPath =
                            inventoryEntity.copy(imagePath = localPath ?: inventoryEntity.imagePath)
                        inventoryDao.insert(inventoryWithUpdatedPath)
                    } else {
                        if (localPath != null) {
                            inventoryDao.updateSyncedStatus(inventoryEntity.id, true)
                        }
                    }
                }

                return@withContext inventoryDao.getInventoriesByUserId(userId)
                    .map { it.toInventory() }
            }
        }

        return@withContext localInventories
    }

    suspend fun downloadImageToInternalStorage(url: String, inventoryId: Int): String? =
        withContext(Dispatchers.IO) {
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
        return@withContext animalRepository.getAllAnimals()
    }

    private fun copyImageToInternalStorageIfNeeded(imagePath: String): String {
        val context = Vacapp.instance.applicationContext
        val sourceFile = File(imagePath)

        if (sourceFile.absolutePath.startsWith(context.filesDir.absolutePath)) {
            return imagePath
        }

        val destDir = File(context.filesDir, "inventories")
        if (!destDir.exists()) destDir.mkdirs()

        val destFile = File(destDir, "inventory_${System.currentTimeMillis()}.jpg")
        sourceFile.copyTo(destFile, overwrite = true)

        return destFile.absolutePath
    }
}
