package pe.edu.upc.vacapp.home.data.di

import pe.edu.upc.vacapp.home.data.local.NextCampaignDao
import pe.edu.upc.vacapp.home.data.local.UserInfoDao
import pe.edu.upc.vacapp.home.data.remote.UserInfoService
import pe.edu.upc.vacapp.home.data.repository.UserInfoRepository
import pe.edu.upc.vacapp.shared.data.di.SharedDataModule.getAppDatabase
import pe.edu.upc.vacapp.shared.data.di.SharedDataModule.getRetrofit

object DataModule {
    fun getUserInfoRepository(): UserInfoRepository {
        return UserInfoRepository(getUserInfoService(), getUserInfoDao(), getNextCampaignDao())
    }

    fun getNextCampaignDao(): NextCampaignDao {
        return getAppDatabase().nextCampaignDao()
    }

    fun getUserInfoDao(): UserInfoDao {
        return getAppDatabase().userInfoDao()
    }

    fun getUserInfoService(): UserInfoService {
        return getRetrofit().create(UserInfoService::class.java)
    }
}