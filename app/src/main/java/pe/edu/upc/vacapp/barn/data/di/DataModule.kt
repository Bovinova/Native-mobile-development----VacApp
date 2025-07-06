package pe.edu.upc.vacapp.barn.data.di

import pe.edu.upc.vacapp.barn.data.local.BarnDao
import pe.edu.upc.vacapp.barn.data.remote.BarnService
import pe.edu.upc.vacapp.barn.data.repository.BarnRepository
import pe.edu.upc.vacapp.shared.data.di.SharedDataModule.getAppDatabase
import pe.edu.upc.vacapp.shared.data.di.SharedDataModule.getRetrofit

object DataModule {
    fun getBarnRepository(): BarnRepository{
        return BarnRepository(getBarnService(), getBarnDao())
    }

    fun getBarnDao(): BarnDao {
        return getAppDatabase().barnDao()
    }

    fun getBarnService(): BarnService{
        return getRetrofit().create(BarnService::class.java)
    }
}