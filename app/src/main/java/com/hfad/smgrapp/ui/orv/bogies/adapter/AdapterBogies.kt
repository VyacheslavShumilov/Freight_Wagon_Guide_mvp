package com.hfad.smgrapp.ui.orv.bogies.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hfad.smgrapp.databinding.ItemBogiesBinding
import com.hfad.smgrapp.model.Bogies

class AdapterBogies(
    private var bogiesList: ArrayList<Bogies>,
    private val listener: SetOnClickListener
) : RecyclerView.Adapter<AdapterBogies.ViewHolder>() {


    inner class ViewHolder(var binding: ItemBogiesBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindView(bogies: Bogies) {
            itemView.setOnClickListener {
                listener.onClickBogie(bogies.modelBogie)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemBogiesBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val bogies = bogiesList[position]
        with(holder.binding) {
            modelBogieTextView.text = bogies.modelBogie
            bogieFactoryTextView.text = bogies.factory
            holder.bindView(bogies)
        }
    }


    override fun getItemCount(): Int = bogiesList.size

    interface SetOnClickListener {
        fun onClickBogie(modelBogie: String)
    }
}