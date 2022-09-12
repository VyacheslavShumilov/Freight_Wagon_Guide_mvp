package com.hfad.smgrapp.view.smgr.favourite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.hfad.smgrapp.App
import com.hfad.smgrapp.adapter.AdapterWagonFavourite
import com.hfad.smgrapp.dao.WagonsDao
import com.hfad.smgrapp.databinding.ActivityFavouriteWagonsBinding
import com.hfad.smgrapp.model.WagonsFavourite
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavouriteWagonsActivity : AppCompatActivity(), AdapterWagonFavourite.OnClickListener {

    private lateinit var binding: ActivityFavouriteWagonsBinding
    private lateinit var appDao: WagonsDao
    private lateinit var adapterWagonFavourite: AdapterWagonFavourite
    private var favourite: ArrayList<WagonsFavourite> = arrayListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavouriteWagonsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        appDao = (applicationContext as App).getDatabase().wagonsDao()

        lifecycleScope.launch(Dispatchers.IO) {
            favourite.addAll(appDao.getAllFavouriteWagons())
            adapterWagonFavourite = AdapterWagonFavourite(favourite, this@FavouriteWagonsActivity)
            binding.recyclerView.adapter = adapterWagonFavourite
        }

        binding.toolbar.clickBackBtn.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onDeleteFavourite(wagonsFavourite: WagonsFavourite) {
        lifecycleScope.launch(Dispatchers.IO) {
            appDao.deleteWagon(wagonsFavourite)
        }

    }
}