package com.hfad.smgrapp

import android.app.Application
import com.hfad.smgrapp.service.ServicesLocator

class App: Application() {

    lateinit var servicesLocator: ServicesLocator

    override fun onCreate() {
        super.onCreate()
        servicesLocator = ServicesLocator()
    }
}