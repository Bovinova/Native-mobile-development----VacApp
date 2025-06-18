package pe.edu.upc.vacapp.animal.data.di

import pe.edu.upc.vacapp.animal.data.remote.AnimalService
import pe.edu.upc.vacapp.animal.data.repository.AnimalRepository
import pe.edu.upc.vacapp.shared.data.di.SharedDataModule.getRetrofit

object DataModule {
    fun getAnimalService(): AnimalService {
        return getRetrofit().create(AnimalService::class.java)
    }

    fun getAnimalRepository(): AnimalRepository {
        return AnimalRepository(getAnimalService())
    }
}