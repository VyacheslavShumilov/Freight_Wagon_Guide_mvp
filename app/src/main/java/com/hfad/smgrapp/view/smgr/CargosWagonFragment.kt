package com.hfad.smgrapp.view.smgr

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hfad.smgrapp.adapter.AdapterCargos
import com.hfad.smgrapp.controller.CargosController
import com.hfad.smgrapp.databinding.FragmentCargosWagonBinding
import com.hfad.smgrapp.model.Cargos
import com.hfad.smgrapp.model.Wagons


class CargosWagonFragment(var wagons: Wagons) : Fragment(), ICargosView {

    private lateinit var binding: FragmentCargosWagonBinding
    private var cargosController: CargosController? = null
    private var adapterCargos = AdapterCargos()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCargosWagonBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cargosController = CargosController(this)
        (cargosController as CargosController).onCargosList()

        binding.wagonModel.text = wagons.model
    }

    override fun onSuccessList(cargos: ArrayList<Cargos>) {
        for (i in cargos) {
            if (i.model == wagons.model) {
                Log.d("MODELS", i.toString())
                adapterCargos.addItem(i)
                adapterCargos.notifyDataSetChanged()
            }
        }
        binding.recyclerView.adapter = adapterCargos
    }

    override fun error(errMessage: String) {
        binding.layoutNotConnection.visibility = View.VISIBLE
        binding.btnClickReply.setOnClickListener {
            (cargosController as CargosController).onCargosList()
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