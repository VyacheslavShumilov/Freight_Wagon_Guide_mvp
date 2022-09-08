package com.hfad.smgrapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "wagonsFavourite")
data class WagonsFavourite(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "modelCode") val modelCode: String,
    @ColumnInfo(name = "model") val model: String,
    @ColumnInfo(name = "photoURL") val photoURL: String,
    @ColumnInfo(name = "rod") val rod: String,
    @ColumnInfo(name = "yearOfRelease") val yearOfRelease: String,
    @ColumnInfo(name = "yearEndOfRelease") val yearEndOfRelease: String,
//    @ColumnInfo(name = "capacity") val capacity: String,
//    @ColumnInfo(name = "property") val property: String,
//    @ColumnInfo(name = "specialization") val specialization: String,
//    @ColumnInfo(name = "material") val material: String,
//    @ColumnInfo(name = "factory") val factory: String,
//    @ColumnInfo(name = "tareMin") val tareMin: String,
//    @ColumnInfo(name = "tareMax") val tareMax: String,
//    @ColumnInfo(name = "tareMinExp") val tareMinExp: String,
//    @ColumnInfo(name = "boltedConnection") val boltedConnection: String,
//    @ColumnInfo(name = "length") val length: String,
//    @ColumnInfo(name = "numAxles") val numAxles: String,
//    @ColumnInfo(name = "axialLoad") val axialLoad: String,
//    @ColumnInfo(name = "footbridge") val footbridge: String,
//    @ColumnInfo(name = "volume") val volume: String,
//    @ColumnInfo(name = "calibration") val calibration: String,
//    @ColumnInfo(name = "bogie") val bogie: String,
//    @ColumnInfo(name = "size") val size: String,
//    @ColumnInfo(name = "serviceLife") val serviceLife: String,
//    @ColumnInfo(name = "long") val long: String,
//    @ColumnInfo(name = "inventoryNum") val inventoryNum: String,
//    @ColumnInfo(name = "typeOfOwnCar") val typeOfOwnCar: String,
//    @ColumnInfo(name = "drAftRelease") val drAftRelease: String,
//    @ColumnInfo(name = "dRaftDRto1KR") val drAftDrTo1Kr: String,
//    @ColumnInfo(name = "dRaftDRaft1KR") val drAftDraft1Kr: String,
//    @ColumnInfo(name = "dRaftKR") val drAftKr: String,
//    @ColumnInfo(name = "krAftRelease") val krAftRelease: String,
//    @ColumnInfo(name = "kRaftKR") val krAftKr: String,
//    @ColumnInfo(name = "drAftReleaseRepProbKm") val drAftReleaseRepProbKm: String,
//    @ColumnInfo(name = "drAftReleaseRepYears") val drAftReleaseRepYears: String,
//    @ColumnInfo(name = "drAftDrRepProbKm") val drAftDrRepProbKm: String,
//    @ColumnInfo(name = "drAftDrRepProbYears") val drAftDrRepProbYears: String,
//    @ColumnInfo(name = "drAftKrRepProbKm") val drAftKrRepProbKm: String,
//    @ColumnInfo(name = "drAftKrRepProbYears") val drAftKrRepProbYears: String,
//    @ColumnInfo(name = "drAftKrpRepProbKm") val drAftKrpRepProbKm: String,
//    @ColumnInfo(name = "drAftKrpRepProbYears") val drAftKrpRepProbYears: String,
//    @ColumnInfo(name = "continueTU") val continueTu: String,
//    @ColumnInfo(name = "dRaftKRPTU") val drAftKrpTu: String,
//    @ColumnInfo(name = "kRaftKRPTU") val krAftKrpTu: String
)