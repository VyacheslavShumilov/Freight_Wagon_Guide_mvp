package com.hfad.smgrapp.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.hfad.smgrapp.model.Wagons
import com.hfad.smgrapp.model.WagonsFavourite

@Dao
interface WagonsDao {
    @Query("SELECT * FROM wagonsFavourite")
    fun getAllFavouriteWagons(): List<WagonsFavourite>

    @Query("SELECT * FROM wagonsFavourite")
    fun getFavouriteWagon(): WagonsFavourite

    @Insert
    fun insertWagon(wagon: WagonsFavourite)

    @Delete
    fun deleteWagon(wagon: WagonsFavourite)

    @Query("DELETE FROM wagonsFavourite")
    fun deleteAll()

}