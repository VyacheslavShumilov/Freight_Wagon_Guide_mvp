package com.hfad.smgrapp.view.smgr

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import com.hfad.smgrapp.adapter.AdapterSmgr
import com.hfad.smgrapp.controller.SmgrController
import com.hfad.smgrapp.databinding.ActivityWagonsBinding
import com.hfad.smgrapp.model.Wagons
import java.io.Serializable
import java.util.*
import kotlin.collections.ArrayList


class WagonsActivity : AppCompatActivity(), ISmgrView, AdapterSmgr.OnClickListener {

    private lateinit var binding: ActivityWagonsBinding
    private var smgrController: SmgrController? = null
    lateinit var adapterSmgr: AdapterSmgr
    var wagonsListFilter: ArrayList<Wagons> = arrayListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWagonsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        smgrController = SmgrController(this)
        (smgrController as SmgrController).onSmgrList()

        adapterSmgr = AdapterSmgr(wagonsListFilter, this)
        binding.recyclerView.adapter = adapterSmgr

  }


    override fun onSuccessList(wagons: ArrayList<Wagons>) {
        wagonsListFilter.addAll(wagons)
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(text: String?): Boolean {
                for (i in wagons) {
                    if (i.model.lowercase(Locale.getDefault())
                            .contains(text!!.lowercase(Locale.getDefault()))
                    ) {
                        wagonsListFilter.add(i)
                    }
                }

                if (text!!.isEmpty()){

                }else{
                    adapterSmgr.filtersList(wagonsListFilter)
                }

                return true
            }
        })
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
}

