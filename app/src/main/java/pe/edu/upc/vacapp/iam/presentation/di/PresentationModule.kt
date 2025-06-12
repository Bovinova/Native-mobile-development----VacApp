package pe.edu.upc.vacapp.iam.presentation.di

import pe.edu.upc.vacapp.iam.data.di.DataModule.getAuthRepository
import pe.edu.upc.vacapp.iam.presentation.viewmodel.LoginViewModel

object PresentationModule {
    fun getLoginViewModel(): LoginViewModel {
        return LoginViewModel(getAuthRepository())
    }
}