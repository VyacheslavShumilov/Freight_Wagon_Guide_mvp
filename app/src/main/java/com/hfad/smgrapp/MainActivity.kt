package com.hfad.smgrapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hfad.smgrapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnToSmgr.setOnClickListener {
            var intent = Intent(this, SmgrActivity::class.java)
            startActivity(intent)
        }

        binding.btnToOrv.setOnClickListener {

        }
    }
}