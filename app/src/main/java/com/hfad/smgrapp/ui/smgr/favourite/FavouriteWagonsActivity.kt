package com.hfad.smgrapp.ui.smgr.favourite

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.hfad.smgrapp.App
import com.hfad.smgrapp.dao.WagonsDao
import com.hfad.smgrapp.databinding.ActivityFavouriteWagonsBinding
import com.hfad.smgrapp.model.Wagons
import com.hfad.smgrapp.model.WagonsFavourite
import com.hfad.smgrapp.ui.smgr.WagonActivity
import com.hfad.smgrapp.ui.smgr.favourite.adapter.AdapterWagonFavourite
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.Serializable

class FavouriteWagonsActivity : AppCompatActivity(), AdapterWagonFavourite.OnClickListener {

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

            runOnUiThread {
                adapterWagonFavourite = AdapterWagonFavourite(favourite, this@FavouriteWagonsActivity)
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
        lifecycleScope.launch(Dispatchers.IO) {
            appDao.deleteWagon(wagonsFavourite)
        }
    }

    override fun notFavourites() {
        Toast.makeText(this, "Нет сохраненных", Toast.LENGTH_SHORT).show()
    }

    /**
     * NEW — клик по карточке избранного открывает WagonActivity
     * с полным набором вкладок (Параметры/Ремонты/Грузы).
     *
     * WagonActivity ожидает Serializable Wagons через extra "WAGON".
     * Мы конвертируем WagonsFavourite → Wagons: основные поля копируются
     * напрямую, отсутствующие в избранном (ремонты, пробеги, т.п.) —
     * заполняются "н.д." (это значение уже корректно отображается на
     * экране параметров через setParamValue с подсветкой warning-цветом).
     */
    override fun onClickFavourite(wagonsFavourite: WagonsFavourite) {
        val wagon = wagonsFavourite.toWagons()
        val intent = Intent(this, WagonActivity::class.java)
        intent.putExtra("WAGON", wagon as Serializable)
        startActivity(intent)
    }

    /**
     * Конвертация WagonsFavourite → Wagons.
     * Поля, которых нет в избранном, заполняются "н.д." — на экране
     * параметров такие значения автоматически подсвечиваются warning-цветом.
     */
    private fun WagonsFavourite.toWagons(): Wagons = Wagons(
        modelCode               = modelCode,
        model                   = model,
        photoURL                = photoURL,
        rod                     = rod,
        yearOfRelease           = yearOfRelease,
        yearEndOfRelease        = yearEndOfRelease,
        capacity                = capacity,
        property                = property,
        specialization          = specialization,
        material                = material,
        factory                 = factory,
        tareMin                 = tareMin,
        tareMax                 = tareMax,
        tareMinExp              = tareMinExp,
        boltedConnection        = boltedConnection,
        length                  = length,
        numAxles                = numAxles,
        axialLoad               = axialLoad,
        footbridge              = footbridge,
        volume                  = volume,
        calibration             = calibration,
        bogie                   = bogie,
        size                    = size,
        serviceLife             = serviceLife,
        long                    = wagonLong,
        inventoryNum            = inventoryNum,
        typeOfOwnCar            = typeOfOwnCar,
        drAftRelease            = drAftRelease,
        drAftDrTo1Kr            = drAftDrTo1Kr,
        drAftDraft1Kr           = drAftDraft1Kr,
        drAftKr                 = drAftKr,
        krAftRelease            = krAftRelease,
        krAftKr                 = krAftKr,
        drAftReleaseRepProbKm   = drAftReleaseRepProbKm,
        drAftReleaseRepYears    = drAftReleaseRepYears,
        drAftDrRepProbKm        = drAftDrRepProbKm,
        drAftDrRepProbYears     = drAftDrRepProbYears,
        drAftKrRepProbKm        = drAftKrRepProbKm,
        drAftKrRepProbYears     = drAftKrRepProbYears,
        drAftKrpRepProbKm       = drAftKrpRepProbKm,
        drAftKrpRepProbYears    = drAftKrpRepProbYears,
        continueTu              = continueTu,
        drAftKrpTu              = drAftKrpTu,
        krAftKrpTu              = krAftKrpTu
    )
}