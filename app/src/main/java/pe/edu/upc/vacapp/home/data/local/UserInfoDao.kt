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
}

