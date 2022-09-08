package com.hfad.smgrapp

import android.app.Application
import androidx.room.Room
import com.hfad.smgrapp.dao.AppDatabase
import com.hfad.smgrapp.service.ServicesLocator

class App: Application() {

    private lateinit var database: AppDatabase

    lateinit var servicesLocator: ServicesLocator

    override fun onCreate() {
        super.onCreate()
        servicesLocator = ServicesLocator()

        database = Room.databaseBuilder(applicationContext,
            AppDatabase::class.java,
            "wagons_database").build()
    }

    fun getDatabase():AppDatabase{
        return database
    }
}