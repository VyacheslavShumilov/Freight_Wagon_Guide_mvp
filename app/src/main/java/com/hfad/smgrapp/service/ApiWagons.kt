package com.hfad.smgrapp.service

import com.hfad.smgrapp.model.Wagons
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiWagons {
    @GET("data.json")
    fun getSmgr(): Call<ArrayList<Wagons>>

    companion object {
        var BASE_URL = "https://raw.githubusercontent.com/VyacheslavShumilov/test/main/"
        fun create(): ApiWagons {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(ApiWagons::class.java)
        }
    }
}