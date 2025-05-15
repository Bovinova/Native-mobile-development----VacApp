package pe.edu.upc.vacapp.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import pe.edu.upc.vacapp.data.model.toBovine
import pe.edu.upc.vacapp.data.remote.BovineService
import pe.edu.upc.vacapp.domain.model.Bovine

class BovineRepository(
    private val bovineService: BovineService,
) {
    suspend fun searchBovine(): List<Bovine> =  withContext(Dispatchers.IO) {
       val response = bovineService.searchBovine()
        if (response.isSuccessful) {
            response.body()?.let { bovineResponse ->
                return@withContext bovineResponse.map {
                    it.toBovine()
                }
            }
        }

        return@withContext emptyList()
    }
}