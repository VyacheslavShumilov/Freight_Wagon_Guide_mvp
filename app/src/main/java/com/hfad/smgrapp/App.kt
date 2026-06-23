package com.hfad.smgrapp

import android.app.Application
import androidx.room.Room
import com.google.android.gms.ads.MobileAds
import com.hfad.smgrapp.dao.AppDatabase
import com.hfad.smgrapp.service.ServicesLocator

class App : Application() {

    private lateinit var database: AppDatabase

    lateinit var servicesLocator: ServicesLocator

    override fun onCreate() {
        super.onCreate()
        servicesLocator = ServicesLocator()

        // CHANGED — добавлена миграция 1→2 для расширения WagonsFavourite.
        // Имя БД "wagons_database" сохранено без изменений.
        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "wagons_database"
        )
            .addMigrations(AppDatabase.MIGRATION_1_2)
            .build()

        // Инициализация AdMob SDK — запускается один раз при старте приложения
        MobileAds.initialize(this) { }
    }

    fun getDatabase(): AppDatabase {
        return database
    }
}