package com.hfad.smgrapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hfad.smgrapp.databinding.ItemBogieComponentsBinding
import com.hfad.smgrapp.model.BogiesComponents

class AdapterBogieComponents : RecyclerView.Adapter<AdapterBogieComponents.ViewHolder>() {

    private var bogieComponentsList: ArrayList<BogiesComponents> = arrayListOf()

    fun addItem(bogiesComponents: BogiesComponents) {
        bogieComponentsList.add(bogiesComponents)
    }

    inner class ViewHolder(var binding: ItemBogieComponentsBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemBogieComponentsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val bogieComponent = bogieComponentsList[position]
        with(holder.binding) {
            elementTextView.text = bogieComponent.element
            bluePrintTextView.text = bogieComponent.bluePrint
        }
    }


    override fun getItemCount(): Int = bogieComponentsList.size
}