package com.hfad.smgrapp.service

import com.hfad.smgrapp.model.Bogies
import com.hfad.smgrapp.model.BogiesComponents
import com.hfad.smgrapp.model.Cargos
import com.hfad.smgrapp.model.Wagons
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiWagons {
    @GET("dataWagons.json")
    fun getSmgr(): Call<ArrayList<Wagons>>

    @GET("dataCargos.json")
    fun getCargos(): Call<ArrayList<Cargos>>

    @GET("dataBogies.json")
    fun getBogies(): Call<ArrayList<Bogies>>

    @GET("dataBogiesComponents.json")
    fun getBogiesComponents(): Call<ArrayList<BogiesComponents>>

    companion object {
        var BASE_URL = "https://raw.githubusercontent.com/VyacheslavShumilov/JsonSMGR/main/"
        fun create(): ApiWagons {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(ApiWagons::class.java)
        }
    }
}