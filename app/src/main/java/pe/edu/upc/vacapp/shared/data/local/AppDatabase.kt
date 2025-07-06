package pe.edu.upc.vacapp.shared.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import pe.edu.upc.vacapp.barn.data.local.BarnDao
import pe.edu.upc.vacapp.barn.data.model.BarnEntity
import pe.edu.upc.vacapp.campaign.data.local.CampaignDao
import pe.edu.upc.vacapp.campaign.data.model.CampaignEntity
import pe.edu.upc.vacapp.home.data.local.NextCampaignDao
import pe.edu.upc.vacapp.home.data.local.UserInfoDao
import pe.edu.upc.vacapp.home.data.model.NextCampaignEntity
import pe.edu.upc.vacapp.home.data.model.UserInfoEntity

@Database(
    entities = [
        BarnEntity::class,
        CampaignEntity::class,
        UserInfoEntity::class,
        NextCampaignEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun barnDao(): BarnDao
    abstract fun campaignDao(): CampaignDao
    abstract fun userInfoDao(): UserInfoDao
    abstract fun nextCampaignDao(): NextCampaignDao
}