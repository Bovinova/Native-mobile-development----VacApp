package pe.edu.upc.vacapp.barn.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import pe.edu.upc.vacapp.barn.data.local.BarnDao
import pe.edu.upc.vacapp.barn.data.model.BarnEntity
import pe.edu.upc.vacapp.barn.data.model.CreateBarnRequest
import pe.edu.upc.vacapp.barn.data.remote.BarnService
import pe.edu.upc.vacapp.barn.domain.model.Barn
import pe.edu.upc.vacapp.shared.createPendingOperation
import pe.edu.upc.vacapp.shared.data.local.JwtStorage
import pe.edu.upc.vacapp.shared.data.local.PendingOperationDao
import pe.edu.upc.vacapp.shared.extractErrorMessage
import pe.edu.upc.vacapp.shared.isOnline

class BarnRepository(
    private val barnService: BarnService,
    private val barnDao: BarnDao,
    private val pendingOperationDao: PendingOperationDao
) {
    suspend fun addBarn(barn: Barn) = withContext(Dispatchers.IO) {
        val userId = JwtStorage.getUserId()

        if (userId == null) {
            throw Exception("User not authenticated")
        }

        val barnEntity = BarnEntity.fromBarn(barn, userId)
        barnDao.insert(barnEntity)

        if (isOnline()) {
            try {
                val data = CreateBarnRequest.fromBarn(barn)
                val response = barnService.createBarn(data)

                if (response.isSuccessful) {
                    val barnFromApi = response.body()

                    if (barnFromApi != null) {
                        // Eliminar el barn temporal (id 0)
                        barnDao.deleteById(barn.id)

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
                val pendingOperation = createPendingOperation(
                    entity = "barn",
                    data = barn,
                    localId = barn.id
                )
                pendingOperationDao.insert(pendingOperation)
                throw Exception("Error de red: ${e.message}")
            }
        } else {
            val pendingOperation = createPendingOperation(
                entity = "barn",
                data = barn,
                localId = barn.id
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