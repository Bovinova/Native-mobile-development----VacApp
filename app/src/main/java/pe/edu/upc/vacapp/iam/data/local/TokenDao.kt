package pe.edu.upc.vacapp.iam.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import pe.edu.upc.vacapp.iam.data.model.TokenEntity

@Dao
interface TokenDao {
    @Query("SELECT * FROM token WHERE id = 1")
    suspend fun getToken(): TokenEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveToken(tokenEntity: TokenEntity)

    @Delete
    suspend fun deleteToken(tokenEntity: TokenEntity)
}