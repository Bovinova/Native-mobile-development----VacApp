package pe.edu.upc.vacapp.shared.data.di

import androidx.room.Room
import pe.edu.upc.vacapp.Vacapp
import pe.edu.upc.vacapp.shared.data.local.AppDatabase
import pe.edu.upc.vacapp.shared.data.remote.ApiConstants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object SharedDataModule {
    // Singleton instance of Retrofit and AppDatabase
    private var dbInstance: AppDatabase? = null
    private var retrofitInstance: Retrofit? = null

    fun getRetrofit(): Retrofit {
        if (retrofitInstance == null) {
            retrofitInstance = Retrofit.Builder()
                .baseUrl(ApiConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofitInstance!!
    }

    fun getAppDatabase(): AppDatabase {
        if (dbInstance == null) {
            dbInstance = Room.databaseBuilder(
                Vacapp.instance.applicationContext,
                AppDatabase::class.java,
                "vacapp-db"
            ).build()
        }
        return dbInstance!!
    }
}
