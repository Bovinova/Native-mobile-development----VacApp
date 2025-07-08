package pe.edu.upc.vacapp.shared.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import pe.edu.upc.vacapp.animal.data.local.AnimalDao
import pe.edu.upc.vacapp.animal.data.model.AnimalEntity
import pe.edu.upc.vacapp.barn.data.local.BarnDao
import pe.edu.upc.vacapp.barn.data.model.BarnEntity
import pe.edu.upc.vacapp.campaign.data.local.CampaignDao
import pe.edu.upc.vacapp.campaign.data.model.CampaignEntity
import pe.edu.upc.vacapp.home.data.local.NextCampaignDao
import pe.edu.upc.vacapp.home.data.local.UserInfoDao
import pe.edu.upc.vacapp.home.data.model.NextCampaignEntity
import pe.edu.upc.vacapp.home.data.model.UserInfoEntity
import pe.edu.upc.vacapp.inventory.data.local.InventoryDao
import pe.edu.upc.vacapp.inventory.data.model.InventoryEntity
import pe.edu.upc.vacapp.shared.data.model.PendingOperationEntity

@Database(
    entities = [
        BarnEntity::class,
        CampaignEntity::class,
        UserInfoEntity::class,
        NextCampaignEntity::class,
        AnimalEntity::class,
        InventoryEntity::class,
        PendingOperationEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun barnDao(): BarnDao
    abstract fun campaignDao(): CampaignDao
    abstract fun userInfoDao(): UserInfoDao
    abstract fun nextCampaignDao(): NextCampaignDao
    abstract fun animalDao(): AnimalDao
    abstract fun inventoryDao(): InventoryDao
    abstract fun pendingOperationDao(): PendingOperationDao
}