package pe.edu.upc.vacapp.home.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import pe.edu.upc.vacapp.home.data.model.UserInfoEntity

@Dao
interface UserInfoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserInfo(userInfo: UserInfoEntity)

    @Query("SELECT * FROM UserInfoEntity WHERE userId = :userId LIMIT 1")
    suspend fun getUserInfoByUserId(userId: Int): UserInfoEntity?

    @Query("UPDATE UserInfoEntity SET totalAnimals = totalAnimals + :amount WHERE userId = :userId")
    suspend fun increaseTotalAnimals(userId: Int, amount: Int = 1)

    @Query("UPDATE UserInfoEntity SET totalCampaigns = totalCampaigns + :amount WHERE userId = :userId")
    suspend fun increaseTotalCampaigns(userId: Int, amount: Int = 1)

    @Query("UPDATE UserInfoEntity SET totalBarns = totalBarns + :amount WHERE userId = :userId")
    suspend fun increaseTotalBarns(userId: Int, amount: Int = 1)

    @Query("UPDATE UserInfoEntity SET totalVaccinations = totalVaccinations + :amount WHERE userId = :userId")
    suspend fun increaseTotalVaccinations(userId: Int, amount: Int = 1)
}

