package pe.edu.upc.vacapp

import android.app.Application

class Vacapp : Application() {
    companion object {
        lateinit var instance: Vacapp
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}
