package com.hfad.smgrapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.hfad.smgrapp.adapter.AdapterSmgr
import com.hfad.smgrapp.controller.SmgrController
import com.hfad.smgrapp.databinding.ActivityWagonsBinding
import com.hfad.smgrapp.model.Smgr


class WagonsActivity : AppCompatActivity(), ISmgrView {

    private lateinit var binding: ActivityWagonsBinding
    private var smgrController: SmgrController? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWagonsBinding.inflate(layoutInflater)
        setContentView(binding.root)

         smgrController = SmgrController(this)
        (smgrController as SmgrController).onSmgrList()

        binding.textNextActivity.setOnClickListener {
            startActivity(Intent(this, WagonActivity::class.java))
        }

    }

    override fun onSuccessList(smgr: ArrayList<Smgr>) {
        val adapterSmgr = AdapterSmgr(smgr)
        binding.recyclerView.adapter = adapterSmgr
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
}