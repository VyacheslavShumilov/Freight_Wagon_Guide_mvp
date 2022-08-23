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
    @SerializedName("Long") var long: String

): Serializable




//"Inventorynum": "908",
//"Typeofowncar": "5913",


//"DRaftreleas": "2",
//"DRaftDRto1KR": "1",
//"DRaftDRaft1KR": "1",
//"DRaftKR": "2",
//"KRaftrelease": "10",
//"KRaftKR": "8",
//"DRaftreleaserepprobkm": "210",
//"DRaftreleaserepyears": "3",
//"DRaftDRrepprobkm": "110",
//"DRaftDRrepprobyears": "2",
//"DRaftKRrepprobkm": "160",
//"DRaftKRrepprobyears": "2",
//"DRaftKRPrepprobkm": "210",
//"DRaftKRPrepprobyears": "3",
//"ContinueTU": "0",
//"DRaftKRPTU": "0",
//"KRaftKRPTU": "0",
//"Long": "отсутствует"