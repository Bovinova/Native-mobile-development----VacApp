package pe.edu.upc.vacapp.iam.presentation.di

import pe.edu.upc.vacapp.iam.data.di.DataModule.getAuthRepository
import pe.edu.upc.vacapp.iam.presentation.viewmodel.AuthViewModel

object PresentationModule {
    fun getAuthViewModel(): AuthViewModel {
        return AuthViewModel(getAuthRepository())
    }
}