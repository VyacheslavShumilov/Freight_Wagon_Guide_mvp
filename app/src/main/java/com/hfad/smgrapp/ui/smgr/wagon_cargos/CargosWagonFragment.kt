package com.hfad.smgrapp.ui.smgr.wagon_cargos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hfad.smgrapp.ui.smgr.wagon_cargos.adapter.AdapterWagonCargos
import com.hfad.smgrapp.databinding.FragmentCargosWagonBinding
import com.hfad.smgrapp.model.Cargos
import com.hfad.smgrapp.model.Wagons
import com.hfad.smgrapp.ui.smgr.WagonActivity
import com.hfad.smgrapp.ui.smgr.wagon_cargos.impl.WagonCargosContract
import com.hfad.smgrapp.ui.smgr.wagon_cargos.impl.WagonCargosPresenterImpl


class CargosWagonFragment(var wagons: Wagons) : Fragment(), WagonCargosContract.View {

    private lateinit var binding: FragmentCargosWagonBinding
    private lateinit var presenter: WagonCargosPresenterImpl
    private var adapterWagonCargos = AdapterWagonCargos()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCargosWagonBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter = WagonCargosPresenterImpl()
        presenter.attachView(this)
        presenter.responseData()

        with(binding) {

            toolbar.textView.text = "Перевозимые грузы (ЕТСНГ)"

            wagonModel.text = wagons.model

            toolbar.clickBackBtn.setOnClickListener {
                (requireActivity() as WagonActivity).onBackPressed()
            }

            toolbar.clickHomeBtn.visibility = View.GONE
        }
    }

    override fun onSuccessList(cargos: ArrayList<Cargos>) {
        for (i in cargos) {
            if (i.model == wagons.model && i.modelCode == wagons.modelCode) {
                adapterWagonCargos.addItem(i)
                adapterWagonCargos.notifyDataSetChanged()
            }
        }
        binding.recyclerView.adapter = adapterWagonCargos

        if (adapterWagonCargos.isListEmpty()) {
            binding.recyclerView.visibility = View.GONE
            binding.noCargosTextView.visibility = View.VISIBLE
        } else {
            binding.recyclerView.visibility = View.VISIBLE
            binding.noCargosTextView.visibility = View.GONE
        }
    }


    override fun error(errMessage: String) {
        binding.layoutNotConnection.visibility = View.VISIBLE
        binding.btnClickReply.setOnClickListener {
            presenter.responseData()
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
