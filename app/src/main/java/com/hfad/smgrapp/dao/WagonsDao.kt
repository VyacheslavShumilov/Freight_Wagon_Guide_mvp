package com.hfad.smgrapp.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.hfad.smgrapp.model.WagonsFavourite

@Dao
interface WagonsDao {
    @Query("SELECT * FROM wagonsFavourite  ORDER BY modelCode DESC")
    fun getAllFavouriteWagons(): List<WagonsFavourite>

    @Insert
    fun insertWagon(wagon: WagonsFavourite)

    @Delete
    fun deleteWagon(wagon: WagonsFavourite)

    @Query("SELECT * FROM wagonsFavourite WHERE modelCode =:modelCode")
    fun getWagonFavorite(modelCode: String): WagonsFavourite

    @Query("DELETE FROM wagonsFavourite")
    fun deleteAll()

}