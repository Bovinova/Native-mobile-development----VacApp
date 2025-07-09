package pe.edu.upc.vacapp.shared.data.remote

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.google.gson.Gson
import pe.edu.upc.vacapp.animal.data.di.DataModule.getAnimalService
import pe.edu.upc.vacapp.animal.data.local.AnimalDao
import pe.edu.upc.vacapp.animal.data.model.AddAnimalRequest
import pe.edu.upc.vacapp.animal.data.model.AnimalEntity
import pe.edu.upc.vacapp.animal.data.model.AnimalJson
import pe.edu.upc.vacapp.animal.data.model.toMultipartPart
import pe.edu.upc.vacapp.animal.data.model.toRequestBody
import pe.edu.upc.vacapp.animal.data.remote.AnimalService
import pe.edu.upc.vacapp.animal.domain.model.Animal
import pe.edu.upc.vacapp.barn.data.di.DataModule.getBarnService
import pe.edu.upc.vacapp.barn.data.local.BarnDao
import pe.edu.upc.vacapp.barn.data.model.BarnEntity
import pe.edu.upc.vacapp.barn.data.model.CreateBarnRequest
import pe.edu.upc.vacapp.barn.data.remote.BarnService
import pe.edu.upc.vacapp.barn.domain.model.Barn
import pe.edu.upc.vacapp.campaign.data.di.DataModule.getCampaignService
import pe.edu.upc.vacapp.campaign.data.local.CampaignDao
import pe.edu.upc.vacapp.campaign.data.model.CampaignEntity
import pe.edu.upc.vacapp.campaign.data.model.CreateCampaignRequest
import pe.edu.upc.vacapp.campaign.data.remote.CampaignService
import pe.edu.upc.vacapp.campaign.domain.model.Campaign
import pe.edu.upc.vacapp.inventory.data.di.DataModule.getInventoryService
import pe.edu.upc.vacapp.inventory.data.local.InventoryDao
import pe.edu.upc.vacapp.inventory.data.model.AddInventoryRequest
import pe.edu.upc.vacapp.inventory.data.model.InventoryEntity
import pe.edu.upc.vacapp.inventory.data.remote.InventoryService
import pe.edu.upc.vacapp.inventory.domain.model.Inventory
import pe.edu.upc.vacapp.shared.data.di.SharedDataModule.getAppDatabase
import pe.edu.upc.vacapp.shared.data.local.JwtStorage
import pe.edu.upc.vacapp.shared.data.local.PendingOperationDao
import java.io.File

class SyncWorker(
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {
    private val pendingOperationDao: PendingOperationDao = getAppDatabase().pendingOperationDao()
    private val barnDao: BarnDao = getAppDatabase().barnDao()
    private val barnService: BarnService = getBarnService()
    private val campaignDao: CampaignDao = getAppDatabase().campaignDao()
    private val campaignService: CampaignService = getCampaignService()
    private val animalDao: AnimalDao = getAppDatabase().animalDao()
    private val animalService: AnimalService = getAnimalService()
    private val inventoryDao: InventoryDao = getAppDatabase().inventoryDao()
    private val inventoryService: InventoryService = getInventoryService()

    override suspend fun doWork(): Result {
        val pendingOperations = pendingOperationDao.getAll()

        for (operation in pendingOperations) {
            try {
                when (operation.entity) {

                    "barn" -> {
                        val barn = Gson().fromJson(operation.dataJson, Barn::class.java)
                        val request = CreateBarnRequest.fromBarn(barn)

                        val response = barnService.createBarn(request)

                        if (response.isSuccessful && response.body() != null) {
                            val createdBarn = response.body()!!

                            val relatedCampaignOps =
                                pendingOperationDao.getAll().filter { it.entity == "campaign" }

                            for (op in relatedCampaignOps) {
                                val campaign = Gson().fromJson(op.dataJson, Campaign::class.java)

                                if (op.localId != 0 && op.localId == barn.id) {
                                    val updatedCampaign = campaign.copy(barnId = createdBarn.id)
                                    val updatedJson = Gson().toJson(updatedCampaign)
                                    val updatedOp = op.copy(dataJson = updatedJson)
                                    pendingOperationDao.update(updatedOp)
                                }
                            }

                            val relatedAnimalOps =
                                pendingOperationDao.getAll().filter { it.entity == "animal" }

                            for (op in relatedAnimalOps) {
                                val animal = Gson().fromJson(op.dataJson, Animal::class.java)

                                if (op.localId != 0 && op.localId == barn.id) {
                                    val updatedAnimal = animal.copy(barnId = createdBarn.id)
                                    val updatedJson = Gson().toJson(updatedAnimal)
                                    val updatedOp = op.copy(dataJson = updatedJson)
                                    pendingOperationDao.update(updatedOp)
                                }
                            }

                            barnDao.deleteById(barn.id)

                            val userId = JwtStorage.getUserId() ?: return Result.retry()

                            val barnEntity = BarnEntity(
                                id = createdBarn.id,
                                name = barn.name,
                                limit = barn.limit,
                                userId = userId,
                                synced = true
                            )

                            barnDao.insert(barnEntity)

                            pendingOperationDao.delete(operation)
                        } else {
                            return Result.retry()
                        }
                    }

                    "campaign" -> {
                        val campaign = Gson().fromJson(operation.dataJson, Campaign::class.java)
                        val request = CreateCampaignRequest.fromCampaign(campaign)

                        val response = campaignService.createCampaign(request)

                        if (response.isSuccessful && response.body() != null) {
                            val createdCampaign = response.body()!!

                            campaignDao.deleteById(campaign.id)

                            val userId = JwtStorage.getUserId() ?: return Result.retry()

                            val campaignEntity = CampaignEntity(
                                id = createdCampaign.id,
                                name = campaign.name,
                                description = campaign.description,
                                startDate = createdCampaign.startDate,
                                endDate = createdCampaign.endDate,
                                barnId = createdCampaign.stableId,
                                userId = userId,
                                synced = true
                            )

                            campaignDao.insert(campaignEntity)

                            pendingOperationDao.delete(operation)
                        } else {
                            return Result.retry()
                        }
                    }

                    "animal" -> {
                        val animal =
                            Gson().fromJson(operation.dataJson, AnimalJson::class.java).toAnimal()

                        val file = File(animal.image)

                        val request = AddAnimalRequest.fromAnimal(
                            animal.copy(image = file.absolutePath)
                        )

                        val response = animalService.addAnimal(
                            request.name.toRequestBody(),
                            request.gender.toRequestBody(),
                            request.birthDate.toRequestBody(),
                            request.breed.toRequestBody(),
                            request.location.toRequestBody(),
                            request.stableId.toRequestBody(),
                            request.image.toMultipartPart("FileData")
                        )

                        if (response.isSuccessful) {
                            val createdAnimal = response.body()!!

                            val relatedInventoryOps =
                                pendingOperationDao.getAll().filter { it.entity == "inventory" }

                            for (op in relatedInventoryOps) {
                                val inventory = Gson().fromJson(op.dataJson, Inventory::class.java)

                                if (op.localId != 0 && op.localId == animal.id) {
                                    val updatedInventory =
                                        inventory.copy(bovineId = createdAnimal.id)
                                    val updatedJson = Gson().toJson(updatedInventory)
                                    val updatedOp = op.copy(dataJson = updatedJson)
                                    pendingOperationDao.update(updatedOp)
                                }
                            }

                            animalDao.deleteById(animal.id)

                            val userId = JwtStorage.getUserId() ?: return Result.retry()

                            val animalEntity = AnimalEntity(
                                id = createdAnimal.id,
                                name = animal.name,
                                gender = if (animal.isMale) "male" else "female",
                                birthDate = createdAnimal.birthDate,
                                breed = createdAnimal.breed,
                                location = createdAnimal.location,
                                stableId = createdAnimal.stableId,
                                imagePath = animal.image,
                                age = animal.age,
                                userId = userId,
                                synced = true
                            )

                            animalDao.insert(animalEntity)

                            pendingOperationDao.delete(operation)
                        } else {
                            return Result.retry()
                        }
                    }

                    "inventory" -> {
                        val inventory = Gson().fromJson(operation.dataJson, Inventory::class.java)

                        val file = File(inventory.image)

                        val request = AddInventoryRequest.fromInventory(
                            inventory.copy(image = file.absolutePath)
                        )
                        val response = inventoryService.addInventory(
                            request.name.toRequestBody(),
                            request.vaccineType.toRequestBody(),
                            request.vaccineDate.toRequestBody(),
                            request.bovineId.toRequestBody(),
                            request.image.toMultipartPart("FileData")
                        )

                        if (response.isSuccessful) {
                            val createdInventory = response.body()!!

                            inventoryDao.deleteById(inventory.id)

                            val userId = JwtStorage.getUserId() ?: return Result.retry()

                            val inventoryEntity = InventoryEntity(
                                id = createdInventory.id,
                                name = inventory.name,
                                vaccineType = createdInventory.vaccineType,
                                vaccineDate = createdInventory.vaccineDate,
                                bovineId = createdInventory.bovineId,
                                imagePath = inventory.image,
                                userId = userId,
                                synced = true
                            )

                            inventoryDao.insert(inventoryEntity)

                            pendingOperationDao.delete(operation)
                        } else {
                            return Result.retry()
                        }
                    }
                }
            } catch (e: Exception) {
                return Result.retry()
            }
        }

        return Result.success()
    }
}
