package pe.edu.upc.vacapp.shared.data.remote

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.google.gson.Gson
import pe.edu.upc.vacapp.animal.data.di.DataModule.getAnimalService
import pe.edu.upc.vacapp.animal.data.local.AnimalDao
import pe.edu.upc.vacapp.animal.data.model.AddAnimalRequest
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
import pe.edu.upc.vacapp.campaign.data.model.CreateCampaignRequest
import pe.edu.upc.vacapp.campaign.data.remote.CampaignService
import pe.edu.upc.vacapp.campaign.domain.model.Campaign
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

                                if (op.localId != 0 && op.localId == barn.id ) {
                                    val updatedAnimal = animal.copy(barnId = createdBarn.id)
                                    val updatedJson = Gson().toJson(updatedAnimal)
                                    val updatedOp = op.copy(dataJson = updatedJson)
                                    pendingOperationDao.update(updatedOp)
                                }
                            }

                            barnDao.deleteById(barn.id)

                            val userId = JwtStorage.getUserId() ?: return Result.retry()
                            val barnEntity =
                                BarnEntity.fromBarn(barn.copy(id = createdBarn.id), userId)
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
                            val newCampaignEntity = createdCampaign.toCampaignEntity(userId)
                            campaignDao.insert(newCampaignEntity)

                            pendingOperationDao.delete(operation)
                        } else {
                            return Result.retry()
                        }
                    }

                    "animal" -> {
                        val animal = Gson().fromJson(operation.dataJson, Animal::class.java)

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
                            animalDao.updateSyncedStatus(animal.id, true)

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
