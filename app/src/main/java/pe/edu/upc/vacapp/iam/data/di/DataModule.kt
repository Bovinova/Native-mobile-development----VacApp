package pe.edu.upc.vacapp.iam.data.di

import pe.edu.upc.vacapp.iam.data.local.TokenDao
import pe.edu.upc.vacapp.iam.data.remote.AuthService
import pe.edu.upc.vacapp.iam.data.repository.AuthRepository
import pe.edu.upc.vacapp.shared.data.di.SharedDataModule.getAppDatabase
import pe.edu.upc.vacapp.shared.data.di.SharedDataModule.getRetrofit

object DataModule {
    fun getAuthService(): AuthService {
        return getRetrofit().create(AuthService::class.java)
    }

    fun getAuthRepository(): AuthRepository {
        return AuthRepository(getAuthService(), getTokenDao())
    }

    fun getTokenDao(): TokenDao {
        return getAppDatabase().tokenDao()
    }
}