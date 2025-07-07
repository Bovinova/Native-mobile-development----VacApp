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

    @Query("UPDATE AnimalEntity SET synced = :synced WHERE id = :animalId")
    suspend fun updateSyncedStatus(animalId: Int, synced: Boolean)

    @Query("DELETE FROM AnimalEntity")
    suspend fun clearAnimals()
}
