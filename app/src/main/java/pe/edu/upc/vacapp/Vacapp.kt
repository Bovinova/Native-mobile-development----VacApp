package pe.edu.upc.vacapp

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import pe.edu.upc.vacapp.shared.data.local.JwtStorage

class Vacapp : Application() {
    companion object {
        lateinit var instance: Vacapp
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        AndroidThreeTen.init(this)
        JwtStorage.init(applicationContext)
    }
}
