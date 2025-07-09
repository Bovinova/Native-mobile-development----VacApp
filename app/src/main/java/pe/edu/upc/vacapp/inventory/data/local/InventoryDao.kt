package pe.edu.upc.vacapp.inventory.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import pe.edu.upc.vacapp.inventory.data.model.InventoryEntity

@Dao
interface InventoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(inventory: InventoryEntity)

    @Query("SELECT * FROM InventoryEntity WHERE userId = :userId")
    suspend fun getInventoriesByUserId(userId: Int): List<InventoryEntity>

    @Query("UPDATE InventoryEntity SET synced = :synced WHERE id = :inventoryId")
    suspend fun updateSyncedStatus(inventoryId: Int, synced: Boolean)

    @Query("SELECT * FROM InventoryEntity WHERE id = :id")
    fun getInventoryById(id: Int): InventoryEntity?

    @Query("DELETE FROM InventoryEntity WHERE id = :id")
    fun deleteById(id: Int)

    @Query("SELECT MAX(id) FROM InventoryEntity")
    suspend fun getLastId(): Int?
}
