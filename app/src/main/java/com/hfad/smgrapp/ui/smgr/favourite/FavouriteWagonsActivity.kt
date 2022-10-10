package com.hfad.smgrapp.ui.smgr.favourite

import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.transition.*
import com.hfad.smgrapp.App
import com.hfad.smgrapp.ui.smgr.favourite.adapter.AdapterWagonFavourite
import com.hfad.smgrapp.dao.WagonsDao
import com.hfad.smgrapp.databinding.ActivityFavouriteWagonsBinding
import com.hfad.smgrapp.model.WagonsFavourite
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavouriteWagonsActivity : AppCompatActivity(), AdapterWagonFavourite.OnClickListener {

    private lateinit var binding: ActivityFavouriteWagonsBinding
    private lateinit var appDao: WagonsDao
    private lateinit var adapterWagonFavourite: AdapterWagonFavourite
    private var favourite: ArrayList<WagonsFavourite> = arrayListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavouriteWagonsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding){
            toolbar.textView.text = "Список сравнения"
            toolbar.clickBackBtn.setOnClickListener {
                onBackPressed()
            }
            toolbar.clickHomeBtn.visibility = View.GONE
        }


        appDao = (applicationContext as App).getDatabase().wagonsDao()

        lifecycleScope.launch(Dispatchers.IO) {
            favourite.addAll(appDao.getAllFavouriteWagons())
            adapterWagonFavourite = AdapterWagonFavourite(favourite, this@FavouriteWagonsActivity)
            binding.recyclerView.adapter = adapterWagonFavourite

            withContext(Dispatchers.Main) {
                if (favourite.size == 0) {
                    Toast.makeText(
                        this@FavouriteWagonsActivity,
                        "Список пуст",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    override fun onDeleteFavourite(wagonsFavourite: WagonsFavourite) {
        lifecycleScope.launch(Dispatchers.IO) {
            appDao.deleteWagon(wagonsFavourite)
        }
    }

    override fun notFavourites() {
        Toast.makeText(this, "Нет сохраненных", Toast.LENGTH_SHORT).show()
    }

    override fun onExplode(view: View) {
        val rect = Rect()
        view.getGlobalVisibleRect(rect)

        val explode = Explode().apply {
            epicenterCallback = object : Transition.EpicenterCallback() {
                override fun onGetEpicenter(transition: Transition): Rect = rect
            }
            duration = 1000L
        }

        val animSet = TransitionSet()
            .addTransition(explode)
            .addTransition(Fade().addTarget(view))

        TransitionManager.beginDelayedTransition(binding.recyclerView, animSet)
    }


}