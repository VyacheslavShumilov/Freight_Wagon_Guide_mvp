package com.hfad.smgrapp.view.smgr.favourite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.hfad.smgrapp.App
import com.hfad.smgrapp.R
import com.hfad.smgrapp.dao.WagonsDao
import kotlinx.coroutines.launch

class FavouriteWagonsActivity : AppCompatActivity() {

    private lateinit var appDao:WagonsDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favourite_wagons)

        appDao = (applicationContext as App).getDatabase().wagonsDao()


        lifecycleScope.launch {
            val list = appDao.getAllFavouriteWagons() as ArrayList

        }
    }
}