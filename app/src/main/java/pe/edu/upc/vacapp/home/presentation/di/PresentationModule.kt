package pe.edu.upc.vacapp.home.presentation.di

import pe.edu.upc.vacapp.home.data.di.DataModule.getUserInfoRepository
import pe.edu.upc.vacapp.home.presentation.viewmodel.HomeViewModel

object PresentationModule {
    fun getHomeViewModel(): HomeViewModel {
        return HomeViewModel(getUserInfoRepository())
    }
}