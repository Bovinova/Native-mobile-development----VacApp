package pe.edu.upc.vacapp.shared.data.remote

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.google.gson.Gson
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

class SyncWorker(
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {

    private val pendingOperationDao: PendingOperationDao = getAppDatabase().pendingOperationDao()
    private val barnDao: BarnDao = getAppDatabase().barnDao()
    private val barnService: BarnService = getBarnService()
    private val campaignDao: CampaignDao = getAppDatabase().campaignDao()
    private val campaignService: CampaignService = getCampaignService()

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

                            // Actualizamos el barnId en la tabla de campaigns
                            campaignDao.updateBarnId(barn.id, createdBarn.id)

                            // Actualizamos el barnId en las operaciones pendientes que usan este barn
                            val relatedOperations =
                                pendingOperationDao.getAll().filter { it.entity == "campaign" }

                            for (op in relatedOperations) {
                                val campaign = Gson().fromJson(op.dataJson, Campaign::class.java)

                                if (campaign.barnId == barn.id) {
                                    val updatedCampaign = campaign.copy(barnId = createdBarn.id)
                                    val updatedJson = Gson().toJson(updatedCampaign)

                                    // Creamos una nueva pendingOperation actualizada
                                    val updatedOperation = op.copy(dataJson = updatedJson)
                                    pendingOperationDao.update(updatedOperation)
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
                }
            } catch (e: Exception) {
                return Result.retry()
            }
        }

        return Result.success()
    }
}
