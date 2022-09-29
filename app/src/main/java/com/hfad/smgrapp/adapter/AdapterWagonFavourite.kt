package com.hfad.smgrapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hfad.smgrapp.databinding.ItemWagonFavouriteBinding
import com.hfad.smgrapp.model.WagonsFavourite

class AdapterWagonFavourite(
    private val wagonsFavourites: ArrayList<WagonsFavourite>,
    private val listener: OnClickListener
) :
    RecyclerView.Adapter<AdapterWagonFavourite.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemWagonFavouriteBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("NotifyDataSetChanged")
        fun bindView(wagonsFavourite: WagonsFavourite) {
            with(binding) {
                modelTextView.text = wagonsFavourite.model
                lengthTextView.text = wagonsFavourite.length
                volumeTextView.text = wagonsFavourite.volume
                axialLoadTextView.text = wagonsFavourite.axialLoad
                capacityTextView.text = wagonsFavourite.capacity
                tareMinExpTextView.text = wagonsFavourite.tareMinExp
                tareMinTextView.text = wagonsFavourite.tareMin
                tareMaxTextView.text = wagonsFavourite.tareMax
                yearReleaseTextView.text = wagonsFavourite.yearOfRelease
                yearEndReleaseTextView.text = wagonsFavourite.yearEndOfRelease
                serviceLifeTextView.text = wagonsFavourite.serviceLife


                deleteFavouriteWagonBtn.setOnClickListener {
                    listener.onDeleteFavourite(wagonsFavourite)
                    notifyDataSetChanged()
                    listener.onExplode(itemView)
                    deleteItem(wagonsFavourite)
                    notifyDataSetChanged()
                }
            }
        }

        private fun deleteItem(wagonsFavourite: WagonsFavourite) {
            wagonsFavourites.remove(wagonsFavourite)
            if (wagonsFavourites.size == 0) {
                listener.notFavourites()
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
        holder.bindView(wagonsFavourites[position])
    }

    override fun getItemCount(): Int = wagonsFavourites.size

    interface OnClickListener {
        fun onDeleteFavourite(wagonsFavourite: WagonsFavourite)
        fun notFavourites()
        fun onExplode(view: View)
    }

}