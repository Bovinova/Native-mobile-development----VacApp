package pe.edu.upc.vacapp.animal.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import pe.edu.upc.vacapp.animal.data.model.AnimalEntity

@Dao
interface AnimalDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAnimal(animal: AnimalEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAnimals(animals: List<AnimalEntity>)

    @Query("SELECT * FROM AnimalEntity")
    suspend fun getAllAnimals(): List<AnimalEntity>

    @Query("SELECT * FROM AnimalEntity WHERE userId = :userId")
    suspend fun getAnimalsByUserId(userId: Int): List<AnimalEntity>

    @Query("UPDATE AnimalEntity SET synced = :synced WHERE id = :animalId")
    suspend fun updateSyncedStatus(animalId: Int, synced: Boolean)

    @Query("DELETE FROM AnimalEntity")
    suspend fun clearAnimals()

    @Query("SELECT * FROM AnimalEntity WHERE id = :id")
    fun getAnimalById(id: Int): AnimalEntity?

    @Query("DELETE FROM AnimalEntity WHERE id = :id")
    fun deleteById(id: Int)

    @Query("SELECT MAX(id) FROM AnimalEntity")
    suspend fun getLastId(): Int?
}

