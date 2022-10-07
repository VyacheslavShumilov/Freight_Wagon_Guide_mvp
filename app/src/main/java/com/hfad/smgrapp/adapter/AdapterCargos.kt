package com.hfad.smgrapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hfad.smgrapp.databinding.ItemCargosBinding
import com.hfad.smgrapp.model.Cargos

class AdapterCargos : RecyclerView.Adapter<AdapterCargos.ViewHolder>() {

    private var cargosList: ArrayList<Cargos> = arrayListOf()

    fun addItem(cargos: Cargos) {
        cargosList.add(cargos)
    }

    inner class ViewHolder(var binding: ItemCargosBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemCargosBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cargos = cargosList[position]
        with(holder.binding) {
            nameEtsngTextView.text = cargos.nameEtsng
            codeEtsngTextView.text = cargos.codeEtsng
        }
    }


    override fun getItemCount(): Int = cargosList.size

    fun isListEmpty(): Boolean {
        if (cargosList.isEmpty()) {
            return true
        }
        return false
    }
}