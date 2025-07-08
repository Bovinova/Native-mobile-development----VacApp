package pe.edu.upc.vacapp.barn.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import pe.edu.upc.vacapp.barn.data.local.BarnDao
import pe.edu.upc.vacapp.barn.data.model.BarnEntity
import pe.edu.upc.vacapp.barn.data.model.CreateBarnRequest
import pe.edu.upc.vacapp.barn.data.remote.BarnService
import pe.edu.upc.vacapp.barn.domain.model.Barn
import pe.edu.upc.vacapp.home.data.local.UserInfoDao
import pe.edu.upc.vacapp.shared.createPendingOperation
import pe.edu.upc.vacapp.shared.data.local.JwtStorage
import pe.edu.upc.vacapp.shared.data.local.PendingOperationDao
import pe.edu.upc.vacapp.shared.extractErrorMessage
import pe.edu.upc.vacapp.shared.isOnline

class BarnRepository(
    private val barnService: BarnService,
    private val barnDao: BarnDao,
    private val pendingOperationDao: PendingOperationDao,
    private val userInfoDao: UserInfoDao
) {
    suspend fun addBarn(barn: Barn) = withContext(Dispatchers.IO) {
        val userId = JwtStorage.getUserId()

        if (userId == null) {
            throw Exception("User not authenticated")
        }

        val lastId = barnDao.getLastId() ?: 0
        val generatedId = lastId + 1

        val barnWithLocalId = barn.copy(id = generatedId)

        val barnEntity = BarnEntity.fromBarn(barnWithLocalId, userId)
        barnDao.insert(barnEntity)
        userInfoDao.increaseTotalBarns(userId)

        if (isOnline()) {
            try {
                val data = CreateBarnRequest.fromBarn(barn)
                val response = barnService.createBarn(data)

                if (response.isSuccessful) {
                    val barnFromApi = response.body()

                    if (barnFromApi != null) {
                        barnDao.deleteById(generatedId)

                        // Insertar el barn con el id real
                        val newBarnEntity = barnFromApi.toBarnEntity(userId)
                        barnDao.insert(newBarnEntity)
                    }
                } else if (response.code() == 400) {
                    val errorMessage = extractErrorMessage(response.errorBody()?.string())
                    throw Exception(errorMessage)
                } else {
                    throw Exception("Unexpected error: ${response.code()}")
                }
            } catch (e: Exception) {
                throw Exception("Error de red: ${e.message}")
            }
        } else {
            val pendingOperation = createPendingOperation(
                entity = "barn", data = barnWithLocalId, localId = barn.id
            )
            pendingOperationDao.insert(pendingOperation)
        }
    }

    suspend fun getBarns(): List<Barn> = withContext(Dispatchers.IO) {
        val userId = JwtStorage.getUserId()

        if (userId == null) {
            throw Exception("User not authenticated")
        }

        val localBarns = barnDao.getBarnsByUserId(userId).map { it.toBarn() }

        if (isOnline()) {
            val response = barnService.getBarns()

            if (response.isSuccessful) {
                val barnsFromApi = response.body()?.map { it.toBarnEntity(userId) } ?: emptyList()

                // Actualizar base local con los datos del servidor
                barnsFromApi.forEach { barnDao.insert(it) }

                return@withContext barnsFromApi.map { it.toBarn() }
            }
        }
        return@withContext localBarns
    }
}