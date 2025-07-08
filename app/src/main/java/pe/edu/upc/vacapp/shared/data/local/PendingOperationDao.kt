package pe.edu.upc.vacapp.shared.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import pe.edu.upc.vacapp.shared.data.model.PendingOperationEntity

@Dao
interface PendingOperationDao {
    @Insert
    suspend fun insert(operation: PendingOperationEntity)

    @Query("SELECT * FROM PendingOperationEntity")
    suspend fun getAll(): List<PendingOperationEntity>

    @Delete
    suspend fun delete(operation: PendingOperationEntity)

    @Query("DELETE FROM PendingOperationEntity")
    suspend fun clearAll()

    @Update
    suspend fun update(pendingOperation: PendingOperationEntity)

    @Query("DELETE FROM PendingOperationEntity WHERE entity = :entity AND localId = :localId")
    suspend fun deleteByEntityAndId(entity: String, localId: Int)
}