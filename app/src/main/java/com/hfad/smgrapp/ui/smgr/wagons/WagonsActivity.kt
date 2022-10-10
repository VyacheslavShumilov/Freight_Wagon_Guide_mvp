package com.hfad.smgrapp.ui.smgr.wagons

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.hfad.smgrapp.R
import com.hfad.smgrapp.ui.smgr.wagons.adapter.AdapterWagons
import com.hfad.smgrapp.databinding.ActivityWagonsBinding
import com.hfad.smgrapp.model.Wagons
import com.hfad.smgrapp.ui.smgr.wagons.impl.WagonsContract
import com.hfad.smgrapp.ui.smgr.wagons.impl.WagonsPresenterImpl
import com.hfad.smgrapp.ui.smgr.WagonActivity
import com.hfad.smgrapp.ui.smgr.favourite.FavouriteWagonsActivity
import java.io.Serializable
import kotlin.collections.ArrayList


class WagonsActivity : AppCompatActivity(), WagonsContract.View, AdapterWagons.OnClickListener {

    private lateinit var binding: ActivityWagonsBinding
    private lateinit var presenter: WagonsPresenterImpl
    lateinit var adapterWagons: AdapterWagons
    lateinit var wagonsListFilter: ArrayList<Wagons>
    lateinit var wagonsList: ArrayList<Wagons>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWagonsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = WagonsPresenterImpl()
        presenter.attachView(this)
        presenter.responseData()

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

            override fun beforeTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(text: Editable?) {
                if (text.toString() != "") {
                    adapterWagons.getFilter().filter(text.toString())
                    binding.recyclerView.recycledViewPool.clear()
                    adapterWagons.notifyDataSetChanged()
                }
            }
        })
    }

    override fun onSuccessList(wagons: ArrayList<Wagons>) {
        wagonsListFilter.addAll(wagons)
        wagonsList.addAll(wagons)
        adapterWagons = AdapterWagons(wagonsList, this)
        binding.recyclerView.adapter = adapterWagons
    }

    override fun error(errMessage: String) {
        binding.layoutNotConnection.visibility = View.VISIBLE
        binding.txtInputLayout.visibility = View.GONE
        binding.btnClickReply.setOnClickListener {
           presenter.responseData()
           binding.txtInputLayout.visibility = View.VISIBLE

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
}

