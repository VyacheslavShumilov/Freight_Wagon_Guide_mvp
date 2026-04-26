package com.hfad.smgrapp.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.hfad.smgrapp.model.WagonsFavourite

@Database(entities = [WagonsFavourite::class], version = 2)
abstract class AppDatabase : RoomDatabase() {

    abstract fun wagonsDao(): WagonsDao

    companion object {
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                val newColumns = listOf(
                    "long", "inventoryNum", "typeOfOwnCar",
                    "drAftRelease", "drAftDrTo1Kr", "drAftDraft1Kr", "drAftKr",
                    "krAftRelease", "krAftKr",
                    "drAftReleaseRepProbKm", "drAftReleaseRepYears",
                    "drAftDrRepProbKm", "drAftDrRepProbYears",
                    "drAftKrRepProbKm", "drAftKrRepProbYears",
                    "drAftKrpRepProbKm", "drAftKrpRepProbYears",
                    "continueTu", "drAftKrpTu", "krAftKrpTu"
                )
                newColumns.forEach { col ->
                    db.execSQL(
                        "ALTER TABLE wagonsFavourite " +
                                "ADD COLUMN `$col` TEXT NOT NULL DEFAULT 'н.д.'"
                    )
                }
            }
        }
    }
}