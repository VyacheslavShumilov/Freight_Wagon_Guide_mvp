package com.hfad.smgrapp.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.hfad.smgrapp.model.Wagons
import com.hfad.smgrapp.model.WagonsFavourite

interface WagonsDao {
    @Query("SELECT * FROM wagonsFavourite")
    fun getAllWagons(): List<WagonsFavourite>

    @Insert
    fun insertWagon(wagon: Wagons)

    @Delete
    fun deleteWagon(wagon: Wagons)

    @Query("DELETE FROM wagonsFavourite")
    fun deleteAll()

}