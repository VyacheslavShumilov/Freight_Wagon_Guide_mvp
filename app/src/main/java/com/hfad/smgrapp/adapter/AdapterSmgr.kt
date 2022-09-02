package com.hfad.smgrapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hfad.smgrapp.databinding.ItemWagonsBinding
import com.hfad.smgrapp.model.Wagons
import kotlin.collections.ArrayList

class AdapterSmgr(private var wagonsList: ArrayList<Wagons>, private val listener: OnClickListener) :
    RecyclerView.Adapter<AdapterSmgr.ViewHolder>() {


    @SuppressLint("NotifyDataSetChanged")
    fun filtersList(wagons: ArrayList<Wagons>) {
        wagonsList = wagons
        notifyDataSetChanged()
    }

    inner class ViewHolder(var binding: ItemWagonsBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemWagonsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val wagons = wagonsList[position]
        with(holder.binding) {
            modelTextView.text = wagons.model
            yearOfReleaseTextView.text = wagons.yearOfRelease
            yearEndOfReleaseTextView.text = wagons.yearEndOfRelease
            capacityTextView.text = wagons.capacity
        }
        holder.itemView.setOnClickListener {
            listener.onClickModel(wagons)
        }
    }

    override fun getItemCount(): Int = this.wagonsList.size

    interface OnClickListener {
        fun onClickModel(wagons: Wagons)
    }
}