package pe.edu.upc.vacapp.shared.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import pe.edu.upc.vacapp.iam.data.local.TokenDao
import pe.edu.upc.vacapp.iam.data.model.TokenEntity

@Database(entities = [TokenEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun tokenDao(): TokenDao
}