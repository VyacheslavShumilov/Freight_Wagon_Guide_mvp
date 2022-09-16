package com.hfad.smgrapp.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hfad.smgrapp.databinding.ItemWagonsBinding
import com.hfad.smgrapp.model.Wagons
import java.util.*
import android.widget.Filter
import com.hfad.smgrapp.model.WagonsFavourite
import kotlin.collections.ArrayList

class AdapterSmgr(
    private var wagonsList: ArrayList<Wagons>,
    private val listener: OnClickListener
) : RecyclerView.Adapter<AdapterSmgr.ViewHolder>() {

    var wagonsListFilters = ArrayList<Wagons>()

    init {
        wagonsListFilters = wagonsList
    }

//    private var wagonsListFavourites: ArrayList<String> = arrayListOf()

//    fun addListFavourites(model: ArrayList<String>) {
//        wagonsListFavourites.addAll(model)
//    }


    inner class ViewHolder(var binding: ItemWagonsBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindView(wagons: Wagons) {
            with(binding) {
                modelTextView.text = wagons.model
                yearOfReleaseTextView.text = wagons.yearOfRelease
                yearEndOfReleaseTextView.text = wagons.yearEndOfRelease
                capacityTextView.text = wagons.capacity

//                for (i in wagonsListFavourites) {
//                    if (i == wagons.modelCode) {
//                        addFavouriteWagonBtn.visibility = View.GONE
//                    }
//                }
            }
        }

        fun bindClickFavourites(wagons: Wagons) {

            with(binding) {
                itemView.setOnClickListener {
                    listener.onClickModel(wagons)
                }

//                addFavouriteWagonBtn.setOnClickListener {
//                    addFavouriteWagonBtn.visibility = View.GONE
//                    listener.onAddFavouriteWagon(wagons)
//                }
            }
        }
    }


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
        try {
            val wagons = wagonsListFilters[position]
            holder.bindView(wagons)
            holder.bindClickFavourites(wagons)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun getItemCount(): Int = wagonsListFilters.size

    @Suppress("UNCHECKED_CAST")
    fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence?): FilterResults {
                Log.d("CHAR_SEARCH", charSequence.toString())
                val charSearch = charSequence.toString()
                if (charSearch.isEmpty()) {
                    wagonsListFilters = wagonsList
                } else {
                    val filteredList = ArrayList<Wagons>()
                    for (row in wagonsList) {
                        if (row.model.lowercase(Locale.ROOT)
                                .contains(charSearch.lowercase(Locale.ROOT))
                        ) {
                            filteredList.add(row)

                            Log.d("CHAR_SEARCH_MODEL", row.model)
                        }
                    }
                    wagonsListFilters = filteredList
                    Log.d("FILTER_LIST", filteredList.toString())
                }
                val filterResults = FilterResults()
                filterResults.values = wagonsListFilters
                return filterResults
            }


            @SuppressLint("UNCHECKED_CAST", "NotifyDataSetChanged")
            override fun publishResults(
                charSequence: CharSequence?,
                filterResults: FilterResults
            ) {
                wagonsListFilters = filterResults.values as ArrayList<Wagons>
                notifyDataSetChanged()
            }
        }
    }


    interface OnClickListener {
        fun onClickModel(wagons: Wagons)
//        fun onAddFavouriteWagon(wagons: Wagons)
//
//        fun onGetFavouriteListWagons()
//        fun onDeleteFavourite(wagonsFavourite: WagonsFavourite)
    }
}

