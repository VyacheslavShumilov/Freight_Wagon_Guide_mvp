package com.hfad.smgrapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "wagonsFavourite")
data class WagonsFavourite(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "ModelCode") val modelCode: String,
    @ColumnInfo(name = "Model") val model: String,
    @ColumnInfo(name = "PhotoURL") val photoURL: String,
    @ColumnInfo(name = "Rod") val rod: String,
    @ColumnInfo(name = "Yearofrelease") val yearOfRelease: String,
    @ColumnInfo(name = "Yearendofrelease") val yearEndOfRelease: String,
    @ColumnInfo(name = "Capacity") val capacity: String,
    @ColumnInfo(name = "Property") val property: String,
    @ColumnInfo(name = "Specialization") val specialization: String,
    @ColumnInfo(name = "Material") val material: String,
    @ColumnInfo(name = "Factory") val factory: String,
    @ColumnInfo(name = "Taremin") val tareMin: String,
    @ColumnInfo(name = "Taremax") val tareMax: String,
    @ColumnInfo(name = "Tareminexp") val tareMinExp: String,
    @ColumnInfo(name = "Boltedconnection") val boltedConnection: String,
    @ColumnInfo(name = "Length") val length: String,
    @ColumnInfo(name = "Numaxles") val numAxles: String,
    @ColumnInfo(name = "Axialload") val axialLoad: String,
    @ColumnInfo(name = "Footbridge") val footbridge: String,
    @ColumnInfo(name = "Volume") val volume: String,
    @ColumnInfo(name = "Calibration") val calibration: String,
    @ColumnInfo(name = "Bogie") val bogie: String,
    @ColumnInfo(name = "Size") val size: String,
    @ColumnInfo(name = "Servicelife") val serviceLife: String,
    @ColumnInfo(name = "Long") val long: String,
    @ColumnInfo(name = "Inventorynum") val inventoryNum: String,
    @ColumnInfo(name = "Typeofowncar") val typeOfOwnCar: String,
    @ColumnInfo(name = "DRaftreleas") val drAftRelease: String,
    @ColumnInfo(name = "DRaftDRto1KR") val drAftDrTo1Kr: String,
    @ColumnInfo(name = "DRaftDRaft1KR") val drAftDraft1Kr: String,
    @ColumnInfo(name = "DRaftKR") val drAftKr: String,
    @ColumnInfo(name = "KRaftrelease") val krAftRelease: String,
    @ColumnInfo(name = "KRaftKR") val krAftKr: String,
    @ColumnInfo(name = "DRaftreleaserepprobkm") val drAftReleaseRepProbKm: String,
    @ColumnInfo(name = "DRaftreleaserepyears") val drAftReleaseRepYears: String,
    @ColumnInfo(name = "DRaftDRrepprobkm") val drAftDrRepProbKm: String,
    @ColumnInfo(name = "DRaftDRrepprobyears") val drAftDrRepProbYears: String,
    @ColumnInfo(name = "DRaftKRrepprobkm") val drAftKrRepProbKm: String,
    @ColumnInfo(name = "DRaftKRrepprobyears") val drAftKrRepProbYears: String,
    @ColumnInfo(name = "DRaftKRPrepprobkm") val drAftKrpRepProbKm: String,
    @ColumnInfo(name = "DRaftKRPrepprobyears") val drAftKrpRepProbYears: String,
    @ColumnInfo(name = "ContinueTU") val continueTu: String,
    @ColumnInfo(name = "DRaftKRPTU") val drAftKrpTu: String,
    @ColumnInfo(name = "KRaftKRPTU") val krAftKrpTu: String
)