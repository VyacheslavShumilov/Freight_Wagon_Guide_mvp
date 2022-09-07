package com.hfad.smgrapp.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hfad.smgrapp.model.WagonsFavourite

@Database(entities = [WagonsFavourite::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun wagonsDao(): WagonsDao
}