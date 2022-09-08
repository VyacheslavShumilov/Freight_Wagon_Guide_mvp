package com.hfad.smgrapp.view.smgr

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.lifecycleScope
import androidx.room.ColumnInfo
import androidx.room.Dao
import com.hfad.smgrapp.App
import com.hfad.smgrapp.R
import com.hfad.smgrapp.adapter.AdapterSmgr
import com.hfad.smgrapp.controller.SmgrController
import com.hfad.smgrapp.dao.WagonsDao
import com.hfad.smgrapp.databinding.ActivityWagonsBinding
import com.hfad.smgrapp.model.Wagons
import com.hfad.smgrapp.model.WagonsFavourite
import com.hfad.smgrapp.view.orv.OrvActivity
import com.hfad.smgrapp.view.smgr.favourite.FavouriteWagonsActivity
import kotlinx.coroutines.launch
import java.io.Serializable
import java.util.*
import kotlin.collections.ArrayList


class WagonsActivity : AppCompatActivity(), ISmgrView, AdapterSmgr.OnClickListener {

    private lateinit var binding: ActivityWagonsBinding
    private var smgrController: SmgrController? = null
    lateinit var adapterSmgr: AdapterSmgr
    lateinit var wagonsListFilter: ArrayList<Wagons>
    lateinit var wagonsList: ArrayList<Wagons>
    lateinit var appDao: WagonsDao


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWagonsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        appDao = (applicationContext as App).getDatabase().wagonsDao()

        smgrController = SmgrController(this)
        (smgrController as SmgrController).onSmgrList()

        wagonsListFilter = ArrayList()
        wagonsList = ArrayList()

        with(binding) {

            toolbar.textView.text = "Модели грузовых вагонов"

            toolbar.clickBackBtn.setOnClickListener {
                onBackPressed()
            }

            toolbar.clickHomeBtn.visibility = View.VISIBLE
            toolbar.clickHomeBtn.setImageDrawable(resources.getDrawable(R.drawable.ic_favourite_list))
            toolbar.clickHomeBtn.setOnClickListener {
                val intent = Intent(this@WagonsActivity, FavouriteWagonsActivity::class.java)
                startActivity(intent)
            }
        }

        binding.searchView.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(text: Editable?) {
                if (text.toString() != "") {
                    adapterSmgr.getFilter().filter(text.toString())
                }
            }
        })
    }

    override fun onSuccessList(wagons: ArrayList<Wagons>) {
        wagonsListFilter.addAll(wagons)
        wagonsList.addAll(wagons)
        adapterSmgr = AdapterSmgr(wagonsListFilter, this)
        binding.recyclerView.adapter = adapterSmgr
        adapterSmgr.notifyDataSetChanged()
    }

    override fun error(errMessage: String) {
        binding.layoutNotConnection.visibility = View.VISIBLE
        binding.btnClickReply.setOnClickListener {
            (smgrController as SmgrController).onSmgrList()
        }
    }

    override fun progress(show: Boolean) {
        if (show) {
            binding.layoutNotConnection.visibility = View.GONE
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.layoutNotConnection.visibility = View.GONE
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onClickModel(wagons: Wagons) {
        val intent = Intent(this, WagonActivity::class.java)
        intent.putExtra("WAGON", wagons as Serializable)
        startActivity(intent)
    }

    override fun onAddFavouriteWagon(wagons: Wagons) {
        lifecycleScope.launch {
            val wagonsFavourite = WagonsFavourite(
                0,
                wagons.modelCode,
                wagons.model,
                wagons.photoURL,
                wagons.rod,
                wagons.yearOfRelease,
                wagons.yearEndOfRelease,
                wagons.capacity,
                wagons.property,
                wagons.specialization,
                wagons.material,
                wagons.factory,
                wagons.tareMin,
                wagons.tareMax,
                wagons.tareMinExp,
                wagons.boltedConnection,
                wagons.length,
                wagons.numAxles,
                wagons.axialLoad,
                wagons.footbridge,
                wagons.volume,
                wagons.calibration,
                wagons.bogie,
                wagons.size,
                wagons.serviceLife,
                wagons.long,
                wagons.inventoryNum,
                wagons.typeOfOwnCar,
                wagons.drAftRelease,
                wagons.drAftDrTo1Kr,
                wagons.drAftDraft1Kr,
                wagons.drAftKr,
                wagons.krAftRelease,
                wagons.krAftKr,
                wagons.drAftReleaseRepProbKm,
                wagons.drAftReleaseRepYears,
                wagons.drAftDrRepProbKm,
                wagons.drAftDrRepProbYears,
                wagons.drAftKrRepProbKm,
                wagons.drAftKrRepProbYears,
                wagons.drAftKrpRepProbKm,
                wagons.drAftKrpRepProbYears,
                wagons.continueTu,
                wagons.drAftKrpTu,
                wagons.krAftKrpTu
            )
            appDao.insertWagon(wagonsFavourite)
        }
    }
}

