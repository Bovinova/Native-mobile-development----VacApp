package pe.edu.upc.vacapp.barn.data.di

import pe.edu.upc.vacapp.barn.data.remote.BarnService
import pe.edu.upc.vacapp.barn.data.repository.BarnRepository
import pe.edu.upc.vacapp.shared.data.di.SharedDataModule.getRetrofit

object DataModule {
    fun getBarnRepository(): BarnRepository{
        return BarnRepository((getBarnService()))
    }
    fun getBarnService(): BarnService{
        return getRetrofit().create(BarnService::class.java)
    }
}