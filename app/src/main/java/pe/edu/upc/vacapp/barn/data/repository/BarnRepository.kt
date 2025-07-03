package pe.edu.upc.vacapp.barn.data.repository

import org.json.JSONObject
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import pe.edu.upc.vacapp.barn.data.model.CreateBarnRequest
import pe.edu.upc.vacapp.barn.data.remote.BarnService
import pe.edu.upc.vacapp.barn.domain.model.Barn
import pe.edu.upc.vacapp.shared.extractErrorMessage

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
        else if (response.code() == 400) {
            val errorMessage = extractErrorMessage(response.errorBody()?.string())
            throw Exception(errorMessage)
        }
        else {
            throw Exception("Unexpected error: ${response.code()}")
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