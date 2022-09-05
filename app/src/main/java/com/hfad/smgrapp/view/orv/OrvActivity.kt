package com.hfad.smgrapp.view.orv

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hfad.smgrapp.App
import com.hfad.smgrapp.databinding.ActivityOrvBinding
import com.hfad.smgrapp.navigator.AppNavigator
import com.hfad.smgrapp.navigator.Screen

class OrvActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOrvBinding
    private lateinit var appNavigator: AppNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrvBinding.inflate(layoutInflater)
        setContentView(binding.root)

        appNavigator = (applicationContext as App).servicesLocator.providerNavigator(this)

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