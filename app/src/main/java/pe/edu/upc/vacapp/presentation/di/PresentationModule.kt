package pe.edu.upc.vacapp.presentation.di

import pe.edu.upc.vacapp.data.di.DataModule
import pe.edu.upc.vacapp.presentation.viewmodel.AuthViewModel
import pe.edu.upc.vacapp.presentation.viewmodel.BovineViewModel

object PresentationModule {
    private val _authViewModel: AuthViewModel by lazy {
        AuthViewModel(DataModule.getAuthRepository())
    }
    private val _bovineViewModel: BovineViewModel by lazy {
        BovineViewModel(DataModule.getBovineRepository())
    }

    fun getBovineViewModel(): BovineViewModel = _bovineViewModel
    fun getAuthViewModel(): AuthViewModel = _authViewModel
}