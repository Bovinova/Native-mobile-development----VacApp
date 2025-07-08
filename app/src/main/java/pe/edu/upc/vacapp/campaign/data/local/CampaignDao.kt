package pe.edu.upc.vacapp.campaign.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import pe.edu.upc.vacapp.campaign.data.model.CampaignEntity

@Dao
interface CampaignDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(campaign: CampaignEntity)

    @Query("SELECT * FROM CampaignEntity WHERE userId = :userId")
    suspend fun getCampaignsByUserId(userId: Int): List<CampaignEntity>

    @Query("UPDATE CampaignEntity SET synced = :synced WHERE id = :campaignId")
    suspend fun updateSyncedStatus(campaignId: Int, synced: Boolean)

    @Delete
    suspend fun delete(campaign: CampaignEntity)

    @Query("DELETE FROM CampaignEntity WHERE id = :id")
    fun deleteById(id: Int)

    @Query("UPDATE CampaignEntity SET barnId = :newBarnId WHERE barnId = :oldBarnId")
    fun updateBarnId(oldBarnId: Int, newBarnId: Int)

    @Query("SELECT MAX(id) FROM CampaignEntity")
    fun getLastId(): Int?
}