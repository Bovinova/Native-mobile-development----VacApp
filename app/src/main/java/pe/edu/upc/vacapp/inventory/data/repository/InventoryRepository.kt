package pe.edu.upc.vacapp.inventory.data.repository

import android.os.Build
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import pe.edu.upc.vacapp.Vacapp
import pe.edu.upc.vacapp.animal.domain.model.Animal
import pe.edu.upc.vacapp.inventory.data.model.AddInventoryRequest
import pe.edu.upc.vacapp.inventory.data.model.toMultipartPart
import pe.edu.upc.vacapp.inventory.data.model.toRequestBody
import pe.edu.upc.vacapp.inventory.data.remote.InventoryService
import pe.edu.upc.vacapp.inventory.domain.model.Inventory
import java.io.File

class InventoryRepository(
    private val inventoryService: InventoryService
) {
    suspend fun addInventory(inventory: Inventory) = withContext(Dispatchers.IO) {
        //TODO: save in ROOM
        val req = AddInventoryRequest.fromInventory(inventory)

        val res = inventoryService.addInventory(
            req.name.toRequestBody(),
            req.vaccineType.toRequestBody(),
            req.vaccineDate.toRequestBody(),
            req.bovineId.toRequestBody(),
            req.image.toMultipartPart("FileData")
        )

        if (res.isSuccessful) {
            //val imgInternalPath = copyFileToInternalStorage(req.image)?.absolutePath ?: ""
            res.body()
        } else {
            throw Exception("Error adding inventory: ${res.errorBody()?.string()}")
        }
    }

    suspend fun getAllInventories(): List<Inventory> = withContext(Dispatchers.IO) {
        val res = inventoryService.getAllInventories()

        if (res.isSuccessful) {
            res.body()?.map { it.toInventory() } ?: emptyList()
        } else {
            throw Exception("Error fetching inventories: ${res.errorBody()?.string()}")
        }
    }

    suspend fun getAnimals(): List<Animal> = withContext(Dispatchers.IO) {
        val response = inventoryService.getAnimals()

        if (response.isSuccessful) {
            return@withContext response.body()?.map {
                it.toAnimal()
            } ?: emptyList()
        }

        return@withContext emptyList()
    }

    suspend fun copyFileToInternalStorage(sourceFile: File): File? = withContext(Dispatchers.IO) {
        val context = Vacapp.instance.applicationContext

        return@withContext try {
            val destFile = File(context.filesDir, "inventory_${System.currentTimeMillis()}.jpg")
            sourceFile.inputStream().use { input ->
                destFile.outputStream().use { output ->
                    input.copyTo(output)
                }
            }
            destFile
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}