package pe.edu.upc.vacapp.home.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import pe.edu.upc.vacapp.home.data.model.NextCampaignEntity

@Dao
interface NextCampaignDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(nextCampaigns: List<NextCampaignEntity>)

    @Query("SELECT * FROM NextCampaignEntity WHERE userInfoId = :userInfoId")
    suspend fun getNextCampaignsByUserInfoId(userInfoId: Int): List<NextCampaignEntity>

    @Query("SELECT * FROM NextCampaignEntity WHERE userInfoId = :userInfoId AND startDate >= :currentDate ORDER BY startDate ASC")
    suspend fun getUpcomingCampaigns(userInfoId: Int, currentDate: String): List<NextCampaignEntity>
}
