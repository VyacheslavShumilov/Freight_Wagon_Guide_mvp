package com.hfad.smgrapp.ui.smgr.wagons.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hfad.smgrapp.databinding.ItemWagonsBinding
import com.hfad.smgrapp.model.Wagons
import android.widget.Filter
import kotlin.collections.ArrayList

class AdapterWagons(
    private var wagonsList: ArrayList<Wagons>,
    private val listener: OnClickListener
) : RecyclerView.Adapter<AdapterWagons.ViewHolder>() {

    var wagonsListFilters = ArrayList<Wagons>()

    init {
        wagonsListFilters = wagonsList
    }


    inner class ViewHolder(var binding: ItemWagonsBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindView(wagons: Wagons) {
            with(binding) {
                modelTextView.text = wagons.model
                yearOfReleaseTextView.text = wagons.yearOfRelease
                yearEndOfReleaseTextView.text = wagons.yearEndOfRelease
                capacityTextView.text = wagons.capacity
            }
        }

        fun bindClickFavourites(wagons: Wagons) {
            itemView.setOnClickListener {
                listener.onClickModel(wagons)
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

//    fun getFilter(): Filter {
//        return object : Filter() {
//            override fun performFiltering(charSequence: CharSequence?): FilterResults {
//                val charSearch = charSequence?.toString()
//                wagonsListFilters = if (charSearch.isNullOrEmpty())
//                    wagonsList
//                else
//                    wagonsList.filter { it.model.contains(charSearch) } as ArrayList<Wagons>
//                return FilterResults().apply { values = wagonsListFilters }
//            }
//
//            override fun publishResults(
//                charSequence: CharSequence?,
//                filterResults: FilterResults?
//            ) {
//                wagonsListFilters = if (filterResults?.values == null)
//                    ArrayList()
//                else
//                    filterResults.values as ArrayList<Wagons>
//                notifyDataSetChanged()
//            }
//        }
//    }




    @Suppress("UNCHECKED_CAST")
    fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence?): FilterResults {
                val charSearch = charSequence?.toString() ?: ""
                if (charSearch.isEmpty()) {
                    wagonsListFilters = wagonsList
                } else {
                    val filteredList = ArrayList<Wagons>()
                    wagonsList.filter {
                        (it.model.contains(charSequence!!))
                    }.forEach {
                        filteredList.add(it)
                    }
                    wagonsListFilters = filteredList
                }
                return FilterResults().apply { values = wagonsListFilters }
            }


            @SuppressLint("UNCHECKED_CAST", "NotifyDataSetChanged")
            override fun publishResults(
                charSequence: CharSequence?,
                filterResults: FilterResults?
            ) {
                wagonsListFilters = if (filterResults?.values == null)
                    ArrayList()
                else
                    filterResults.values as ArrayList<Wagons>
                notifyDataSetChanged()
            }
        }
    }


    interface OnClickListener {
        fun onClickModel(wagons: Wagons)
    }
}

