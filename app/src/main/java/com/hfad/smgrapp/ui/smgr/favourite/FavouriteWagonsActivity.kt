package com.hfad.smgrapp.ui.smgr.favourite

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.hfad.smgrapp.App
import com.hfad.smgrapp.dao.WagonsDao
import com.hfad.smgrapp.databinding.ActivityFavouriteWagonsBinding
import com.hfad.smgrapp.model.WagonsFavourite
import com.hfad.smgrapp.ui.smgr.favourite.adapter.AdapterWagonFavourite
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavouriteWagonsActivity : AppCompatActivity(),
    AdapterWagonFavourite.OnClickListener {

    private lateinit var binding: ActivityFavouriteWagonsBinding
    private lateinit var appDao: WagonsDao
    private lateinit var adapterWagonFavourite: AdapterWagonFavourite
    private var favourite: ArrayList<WagonsFavourite> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavouriteWagonsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            toolbar.textView.text = "Список сравнения"
            toolbar.clickBackBtn.setOnClickListener { onBackPressed() }
            toolbar.clickHomeBtn.visibility = View.GONE
        }

        appDao = (applicationContext as App).getDatabase().wagonsDao()

        // Загрузка избранного из БД
        lifecycleScope.launch(Dispatchers.IO) {
            favourite.addAll(appDao.getAllFavouriteWagons())

            withContext(Dispatchers.Main) {
                adapterWagonFavourite = AdapterWagonFavourite(
                    favourite,
                    this@FavouriteWagonsActivity
                )
                binding.recyclerView.adapter = adapterWagonFavourite

                if (favourite.isEmpty()) {
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
        // Удаление из БД — в IO, без блокировки UI
        lifecycleScope.launch(Dispatchers.IO) {
            appDao.deleteWagon(wagonsFavourite)
        }
    }

    override fun notFavourites() {
        Toast.makeText(this, "Список пуст", Toast.LENGTH_SHORT).show()
    }

    // CHANGED — метод onExplode удалён.
    // Раньше активность запускала Explode transition через TransitionManager,
    // что конфликтовало с RecyclerView и работало нестабильно.
    // Теперь анимация целиком внутри адаптера (ViewPropertyAnimator + notifyItemRemoved),
    // активность ничего о ней не знает.
}