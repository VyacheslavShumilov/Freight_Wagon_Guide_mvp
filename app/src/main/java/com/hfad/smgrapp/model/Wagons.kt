package com.hfad.smgrapp.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Wagons(
    @SerializedName("ModelCode") val modelCode: String,
    @SerializedName("Model") val model: String,
    @SerializedName("PhotoURL") val photoURL: String,
    @SerializedName("Rod") val rod: String,
    @SerializedName("Yearofrelease") val yearOfRelease: String,
    @SerializedName("Yearendofrelease") val yearEndOfRelease: String,
    @SerializedName("Capacity") val capacity: String,
    @SerializedName("Property") val property: String,
    @SerializedName("Specialization") val specialization: String,
    @SerializedName("Material") val material: String,
    @SerializedName("Factory") val factory: String,
    @SerializedName("Taremin") val tareMin: String,
    @SerializedName("Taremax") val tareMax: String,
    @SerializedName("Tareminexp") val tareMinExp: String,
    @SerializedName("Boltedconnection") val boltedConnection: String,
    @SerializedName("Length") val length: String,
    @SerializedName("Numaxles") val numAxles: String,
    @SerializedName("Axialload") val axialLoad: String,
    @SerializedName("Footbridge") val footbridge: String,
    @SerializedName("Volume") val volume: String,
    @SerializedName("Calibration") val calibration: String,
    @SerializedName("Bogie") val bogie: String,
    @SerializedName("Size") val size: String,
    @SerializedName("Servicelife") val serviceLife: String,
    @SerializedName("Long") val long: String,
    @SerializedName("Inventorynum") val inventoryNum: String,
    @SerializedName("Typeofowncar") val typeOfOwnCar: String,
    @SerializedName("DRaftreleas") val drAftRelease: String,
    @SerializedName("DRaftDRto1KR") val drAftDrTo1Kr: String,
    @SerializedName("DRaftDRaft1KR") val drAftDraft1Kr: String,
    @SerializedName("DRaftKR") val drAftKr: String,
    @SerializedName("KRaftrelease") val krAftRelease: String,
    @SerializedName("KRaftKR") val krAftKr: String,
    @SerializedName("DRaftreleaserepprobkm") val drAftReleaseRepProbKm: String,
    @SerializedName("DRaftreleaserepyears") val drAftReleaseRepYears: String,
    @SerializedName("DRaftDRrepprobkm") val drAftDrRepProbKm: String,
    @SerializedName("DRaftDRrepprobyears") val drAftDrRepProbYears: String,
    @SerializedName("DRaftKRrepprobkm") val drAftKrRepProbKm: String,
    @SerializedName("DRaftKRrepprobyears") val drAftKrRepProbYears: String,
    @SerializedName("DRaftKRPrepprobkm") val drAftKrpRepProbKm: String,
    @SerializedName("DRaftKRPrepprobyears") val drAftKrpRepProbYears: String,
    @SerializedName("ContinueTU") val continueTu: String,
    @SerializedName("DRaftKRPTU") val drAftKrpTu: String,
    @SerializedName("KRaftKRPTU") val krAftKrpTu: String
): Serializable


data class Cargos(
    @SerializedName("Model") val model: String,
    @SerializedName("CodeETSNG") val codeEtsng: String,
    @SerializedName("NameETSNG") val nameEtsng: String,
    @SerializedName("ModelCode") val modelCode: String
): Serializable

data class Bogies(
    @SerializedName("ModelBogie") val modelBogie: String,
    @SerializedName("Factory") val factory: String,
    @SerializedName("AxialLoad") val axialLoad: String
): Serializable

data class BogiesComponents(
    @SerializedName("Element") val element: String,
    @SerializedName("BluePrint") val bluePrint: String,
    @SerializedName("ModelBogie") val modelBogie: String,
): Serializable

