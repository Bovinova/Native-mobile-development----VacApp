package pe.edu.upc.vacapp.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import pe.edu.upc.vacapp.data.model.toBovine
import pe.edu.upc.vacapp.data.remote.BovineService
import pe.edu.upc.vacapp.domain.model.Bovine

class BovineRepository(
    private val bovineService: BovineService,
) {
    suspend fun searchBovine(token: String): List<Bovine> = withContext(Dispatchers.IO) {
        val response = bovineService.searchBovine("Bearer $token")
        if (response.isSuccessful) {
            response.body()?.map { it.toBovine() } ?: emptyList()
        } else {
            emptyList()
        }
    }
}