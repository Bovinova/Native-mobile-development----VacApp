package pe.edu.upc.vacapp.presentation.di

import pe.edu.upc.vacapp.data.di.DataModule
import pe.edu.upc.vacapp.presentation.viewmodel.BovineViewModel

object PresentationModule {


    fun getBovineViewModel(): BovineViewModel {
        return BovineViewModel(DataModule.getBovineRepository())
    }
}