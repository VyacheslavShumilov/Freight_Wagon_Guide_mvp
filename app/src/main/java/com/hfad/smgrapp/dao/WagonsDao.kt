package com.hfad.smgrapp.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.hfad.smgrapp.model.WagonsFavourite

@Dao
interface WagonsDao {
    @Query("SELECT * FROM wagonsFavourite")
    fun getAllFavouriteWagons(): List<WagonsFavourite>

    @Insert
    fun insertWagon(wagon: WagonsFavourite)

    @Query("SELECT * FROM wagonsFavourite WHERE id =:id")
    fun getFavouriteWagon (id: Int): WagonsFavourite

    @Delete
    fun deleteWagon(wagon: WagonsFavourite)

    @Query("DELETE FROM wagonsFavourite")
    fun deleteAll()

}