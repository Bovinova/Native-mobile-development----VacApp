package pe.edu.upc.vacapp.barn.data.repository

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import pe.edu.upc.vacapp.barn.data.model.CreateBarnRequest
import pe.edu.upc.vacapp.barn.data.remote.BarnService
import pe.edu.upc.vacapp.barn.domain.model.Barn

class BarnRepository(
    private val barnService: BarnService
) {
    suspend fun addBarn(
        barn: Barn
    ) = withContext(Dispatchers.IO) {
        val data = CreateBarnRequest.fromBarn(barn)
        val response = barnService.createBarn(data)
        if (response.isSuccessful) {
            Log.d("prueba", response.body().toString())
        }
    }

    suspend fun getBarns(): List<Barn> = withContext(Dispatchers.IO) {
        val response = barnService.getBarns()

        if (response.isSuccessful) {
            return@withContext response.body()?.map {
                it.toBarn()
            } ?: emptyList()
        }

        return@withContext emptyList()
    }
}