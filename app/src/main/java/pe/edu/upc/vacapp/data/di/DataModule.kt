package pe.edu.upc.vacapp.data.di

import pe.edu.upc.vacapp.data.remote.ApiConstants
import pe.edu.upc.vacapp.data.remote.BovineService
import pe.edu.upc.vacapp.data.repository.BovineRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.jvm.java

object DataModule {

    fun getBovineRepository(): BovineRepository {
        return BovineRepository(getBovineService())
    }

    fun getBovineService(): BovineService {
        return getRetrofit().create(BovineService::class.java)
    }

    fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(ApiConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}