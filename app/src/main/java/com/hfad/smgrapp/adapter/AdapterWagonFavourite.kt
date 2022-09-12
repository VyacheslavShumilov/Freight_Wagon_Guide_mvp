package com.hfad.smgrapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hfad.smgrapp.databinding.ItemWagonFavouriteBinding
import com.hfad.smgrapp.model.WagonsFavourite

class AdapterWagonFavourite(
    private val wagonsFavourite: ArrayList<WagonsFavourite>,
    private val listener: OnClickListener
) :
    RecyclerView.Adapter<AdapterWagonFavourite.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemWagonFavouriteBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindView(wagonsFavourite: WagonsFavourite) {
            with(binding) {
                modelTextView.text = wagonsFavourite.model
                lengthTextView.text = wagonsFavourite.length
                numAxlesTextView.text = wagonsFavourite.numAxles
                volumeTextView.text = wagonsFavourite.volume
                axialLoadTextView.text = wagonsFavourite.axialLoad
                capacityTextView.text = wagonsFavourite.capacity
                serviceLifeTextView.text = wagonsFavourite.serviceLife
                tareMinExpTextView.text = wagonsFavourite.tareMinExp
                tareMinTextView.text = wagonsFavourite.tareMin
                tareMaxTextView.text = wagonsFavourite.tareMax

                deleteFavouriteWagonBtn.setOnClickListener {
                    listener.onDeleteFavourite(wagonsFavourite)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemWagonFavouriteBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(wagonsFavourite[position])
    }

    override fun getItemCount(): Int = wagonsFavourite.size

    interface OnClickListener {
        fun onDeleteFavourite(wagonsFavourite: WagonsFavourite)
    }
}