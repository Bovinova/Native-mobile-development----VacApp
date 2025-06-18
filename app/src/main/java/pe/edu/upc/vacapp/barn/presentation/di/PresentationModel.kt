package pe.edu.upc.vacapp.barn.presentation.di

import pe.edu.upc.vacapp.barn.data.di.DataModule.getBarnRepository
import pe.edu.upc.vacapp.barn.presentation.viewmodel.BarnViewModel


object PresentationModel {
    fun getBarnViewModel(): BarnViewModel{
        return BarnViewModel(getBarnRepository())
    }
}