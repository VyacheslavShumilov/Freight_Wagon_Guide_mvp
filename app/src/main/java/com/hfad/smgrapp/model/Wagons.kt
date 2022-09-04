package com.hfad.smgrapp.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Wagons(
    @SerializedName("ModelCode") var modelCode: String,
    @SerializedName("Model") var model: String,
    @SerializedName("PhotoURL") var photoURL: String,
    @SerializedName("Rod") var rod: String,
    @SerializedName("Yearofrelease") var yearOfRelease: String,
    @SerializedName("Yearendofrelease") var yearEndOfRelease: String,
    @SerializedName("Capacity") var capacity: String,
    @SerializedName("Property") var property: String,
    @SerializedName("Specialization") var specialization: String,
    @SerializedName("Material") var material: String,
    @SerializedName("Factory") var factory: String,
    @SerializedName("Taremin") var tareMin: String,
    @SerializedName("Taremax") var tareMax: String,
    @SerializedName("Tareminexp") var tareMinExp: String,
    @SerializedName("Boltedconnection") var boltedConnection: String,
    @SerializedName("Length") var length: String,
    @SerializedName("Numaxles") var numAxles: String,
    @SerializedName("Axialload") var axialLoad: String,
    @SerializedName("Footbridge") var footbridge: String,
    @SerializedName("Volume") var volume: String,
    @SerializedName("Calibration") var calibration: String,
    @SerializedName("Bogie") var bogie: String,
    @SerializedName("Size") var size: String,
    @SerializedName("Servicelife") var serviceLife: String,
    @SerializedName("Long") var long: String,
    @SerializedName("Inventorynum") var inventoryNum: String,
    @SerializedName("Typeofowncar") var typeOfOwnCar: String,
    @SerializedName("DRaftreleas") var drAftRelease: String,
    @SerializedName("DRaftDRto1KR") var drAftDrTo1Kr: String,
    @SerializedName("DRaftDRaft1KR") var drAftDraft1Kr: String,
    @SerializedName("DRaftKR") var drAftKr: String,
    @SerializedName("KRaftrelease") var krAftRelease: String,
    @SerializedName("KRaftKR") var krAftKr: String,
    @SerializedName("DRaftreleaserepprobkm") var drAftReleaseRepProbKm: String,
    @SerializedName("DRaftreleaserepyears") var drAftReleaseRepYears: String,
    @SerializedName("DRaftDRrepprobkm") var drAftDrRepProbKm: String,
    @SerializedName("DRaftDRrepprobyears") var drAftDrRepProbYears: String,
    @SerializedName("DRaftKRrepprobkm") var drAftKrRepProbKm: String,
    @SerializedName("DRaftKRrepprobyears") var drAftKrRepProbYears: String,
    @SerializedName("DRaftKRPrepprobkm") var drAftKrpRepProbKm: String,
    @SerializedName("DRaftKRPrepprobyears") var drAftKrpRepProbYears: String,
    @SerializedName("ContinueTU") var continueTu: String,
    @SerializedName("DRaftKRPTU") var drAftKrpTu: String,
    @SerializedName("KRaftKRPTU") var krAftKrpTu: String
): Serializable


data class Cargos(
    @SerializedName("Model") var model: String,
    @SerializedName("CodeETSNG") var codeEtsng: Int,
    @SerializedName("NameETSNG") var nameEtsng: String,
    @SerializedName("ModelCode") var modelCode: Int
): Serializable

data class Bogies(
    @SerializedName("ModelBogie") var modelBogie: String,
    @SerializedName("Factory") var factory: String,
    @SerializedName("AxialLoad") var axialLoad: String
): Serializable

data class BogiesDetails(
    @SerializedName("Element") var element: String,
    @SerializedName("BluePrint") var bluePrint: String,
    @SerializedName("ModelBogie") var modelBogie: String,
): Serializable

