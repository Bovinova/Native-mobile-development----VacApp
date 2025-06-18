package pe.edu.upc.vacapp.home.data.di

import pe.edu.upc.vacapp.home.data.remote.UserInfoService
import pe.edu.upc.vacapp.home.data.repository.UserInfoRepository
import pe.edu.upc.vacapp.shared.data.di.SharedDataModule.getRetrofit

object DataModule {
    fun getUserInfoRepository(): UserInfoRepository {
        return UserInfoRepository(getUserInfoService())
    }

    fun getUserInfoService(): UserInfoService {
        return getRetrofit().create(UserInfoService::class.java)
    }
}