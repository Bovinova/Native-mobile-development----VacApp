package pe.edu.upc.vacapp.animal.presentation.di

import pe.edu.upc.vacapp.animal.data.di.DataModule.getAnimalRepository
import pe.edu.upc.vacapp.animal.presentation.viewmodel.AnimalViewModel

object PresentationModule {
    fun getAnimalViewModel(): AnimalViewModel {
        return AnimalViewModel(getAnimalRepository())
    }
}