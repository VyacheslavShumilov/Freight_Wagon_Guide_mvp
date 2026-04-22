package com.hfad.smgrapp.ui.smgr.wagons.adapter

// REDESIGN v2 — AdapterWagons.kt
// Изменения относительно предыдущей версии:
//   1. Фильтрация по категории теперь идёт не по полю `rod`, а по префиксу
//      модели (первые 2 цифры до "-"). Это единственный надёжный признак типа.
//   2. Убран legacy-метод getFilter() — он не использовался активити,
//      только засорял код.
//   3. Убран избыточный Log.e — out-of-bounds невозможен при корректной
//      работе нотификаций.
//   4. Счётчик видимых элементов доступен снаружи через visibleCount.

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hfad.smgrapp.databinding.ItemWagonsBinding
import com.hfad.smgrapp.model.Wagons

class AdapterWagons(
    private var wagonsList: ArrayList<Wagons>,
    private val listener: OnClickListener
) : RecyclerView.Adapter<AdapterWagons.ViewHolder>() {

    private var wagonsListFilters: ArrayList<Wagons> = ArrayList(wagonsList)

    // Активные фильтры
    private var currentQuery: String = ""
    private var currentPrefixes: List<String> = emptyList() // пустой = "все"

    /** Сколько элементов сейчас видно — удобно для счётчика "найдено X". */
    val visibleCount: Int get() = wagonsListFilters.size

    inner class ViewHolder(val binding: ItemWagonsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(wagons: Wagons) = with(binding) {
            modelTextView.text = wagons.model

            // Бейдж серии — первые 2 символа до "-"
            seriesBadgeText.text = wagons.model.substringBefore("-").take(2)

            // CHANGED: subtitle теперь только диапазон годов.
            // Раньше в конец добавлялось wagons.rod (число осей) без подписи,
            // что читалось как случайная цифра. Убрано до тех пор, пока не появится
            // нормальное UI-решение (например, иконка + "5 осей" где-то в детальном экране).
            val yearEnd = wagons.yearEndOfRelease.ifBlank { "н.в." }
            subtitleTextView.text = "${wagons.yearOfRelease}–$yearEnd"

            capacityTextView.text = "${wagons.capacity} т"

            // Скрытые вьюшки — оставлены для обратной совместимости
            yearOfReleaseTextView.text = wagons.yearOfRelease
            yearEndOfReleaseTextView.text = wagons.yearEndOfRelease

            root.setOnClickListener { listener.onClickModel(wagons) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ItemWagonsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(wagonsListFilters[position])
    }

    override fun getItemCount(): Int = wagonsListFilters.size

    // ── Фильтрация: текст + категория по префиксу ────────────────────────────

    /**
     * Применяет текущие фильтры.
     * @param query текстовый запрос; пустая строка — без фильтра по тексту.
     * @param prefixes список допустимых префиксов модели (например ["10","11"]);
     *                пустой список — без фильтра по категории.
     */
    @SuppressLint("NotifyDataSetChanged")
    fun applyFilters(
        query: String = currentQuery,
        prefixes: List<String> = currentPrefixes
    ) {
        currentQuery = query
        currentPrefixes = prefixes

        wagonsListFilters = wagonsList.filterTo(ArrayList()) { wagon ->
            val matchesQuery = query.isBlank() ||
                    wagon.model.contains(query, ignoreCase = true)

            val matchesCategory = prefixes.isEmpty() ||
                    extractPrefix(wagon.model) in prefixes

            matchesQuery && matchesCategory
        }
        notifyDataSetChanged()
    }

    private fun extractPrefix(model: String): String =
        model.substringBefore("-").take(2)

    interface OnClickListener {
        fun onClickModel(wagons: Wagons)
    }
}