package pe.edu.upc.vacapp.barn.data.repository

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import pe.edu.upc.vacapp.barn.data.local.BarnDao
import pe.edu.upc.vacapp.barn.data.model.BarnEntity
import pe.edu.upc.vacapp.barn.data.model.CreateBarnRequest
import pe.edu.upc.vacapp.barn.data.remote.BarnService
import pe.edu.upc.vacapp.barn.domain.model.Barn
import pe.edu.upc.vacapp.shared.data.local.JwtStorage
import pe.edu.upc.vacapp.shared.extractErrorMessage
import pe.edu.upc.vacapp.shared.isOnline

class BarnRepository(
    private val barnService: BarnService,
    private val barnDao: BarnDao
) {
    suspend fun addBarn(
        barn: Barn
    ) = withContext(Dispatchers.IO) {
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
                    // Marcar como sincronizado
                    barnDao.updateSyncedStatus(barn.id, true)
                } else if (response.code() == 400) {
                    val errorMessage = extractErrorMessage(response.errorBody()?.string())
                    throw Exception(errorMessage)
                } else {
                    throw Exception("Unexpected error: ${response.code()}")
                }
            } catch (e: Exception) {
                // Si falla la sincronización, queda pendiente
                throw Exception("Error de red: ${e.message}")
            }
        }
        // Si está offline, lo dejamos pendiente

        /* val data = CreateBarnRequest.fromBarn(barn)
         val response = barnService.createBarn(data)

         if (response.isSuccessful) {
             Log.d("prueba", response.body().toString())
         }
         else if (response.code() == 400) {
             val errorMessage = extractErrorMessage(response.errorBody()?.string())
             throw Exception(errorMessage)
         }
         else {
             throw Exception("Unexpected error: ${response.code()}")
         }*/
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