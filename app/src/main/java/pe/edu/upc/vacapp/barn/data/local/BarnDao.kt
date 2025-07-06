package pe.edu.upc.vacapp.barn.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import pe.edu.upc.vacapp.barn.data.model.BarnEntity

@Dao
interface BarnDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(barn: BarnEntity)

    @Query("SELECT * FROM BarnEntity WHERE userId = :userId")
    suspend fun getBarnsByUserId(userId: Int): List<BarnEntity>

    @Query("UPDATE BarnEntity SET synced = :synced WHERE id = :barnId")
    suspend fun updateSyncedStatus(barnId: Int, synced: Boolean)

    @Delete
    suspend fun delete(barn: BarnEntity)
}