package com.hfad.smgrapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hfad.smgrapp.ServicesLocator
import com.hfad.smgrapp.databinding.ActivityOrvBinding
import com.hfad.smgrapp.navigator.AppNavigator
import com.hfad.smgrapp.navigator.Screen

class OrvActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOrvBinding
    private lateinit var appNavigator: AppNavigator
    lateinit var servicesLocator: ServicesLocator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrvBinding.inflate(layoutInflater)
        setContentView(binding.root)
        servicesLocator = ServicesLocator()

        appNavigator = servicesLocator.providerNavigator(this)

        if (savedInstanceState == null) {
            appNavigator.navigateTo(Screen.ORV_LIST)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (supportFragmentManager.backStackEntryCount == 0)
            finish()
    }
}