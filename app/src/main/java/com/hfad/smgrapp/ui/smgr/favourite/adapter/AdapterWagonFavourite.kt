package com.hfad.smgrapp.ui.smgr.favourite.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hfad.smgrapp.databinding.ItemWagonFavouriteBinding
import com.hfad.smgrapp.model.WagonsFavourite

class AdapterWagonFavourite(
    private val wagonsFavourites: ArrayList<WagonsFavourite>,
    private val listener: OnClickListener
) : RecyclerView.Adapter<AdapterWagonFavourite.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemWagonFavouriteBinding) :
        RecyclerView.ViewHolder(binding.root) {

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

                // Сброс alpha/scaleX/scaleY больше не нужен:
                // ItemAnimator сам управляет трансформациями ViewHolder'а.
                deleteFavouriteWagonBtn.isEnabled = true

                deleteFavouriteWagonBtn.setOnClickListener {
                    animateAndDelete(wagonsFavourite)
                }
                // NEW — клик по карточке открывает экран параметров вагона.
                // Кнопка удаления обрабатывает свой клик отдельно (см. выше) —
                // тапы по корзине не пробрасываются в карточку.
                root.setOnClickListener {
                    listener.onClickFavourite(wagonsFavourite)
                }
            }
        }

        /**
         * Удаление карточки из избранного.
         *
         * История:
         *  v1: ручной itemView.animate() (fade + scale) + notifyItemRemoved.
         *      Проблема: DefaultItemAnimator при notifyItemRemoved тоже
         *      стартовал свою анимацию (fade + slide), две анимации
         *      конфликтовали → "моргание" в момент перехода.
         *
         *  v2 (текущая): анимация полностью делегирована ItemAnimator'у
         *      RecyclerView. Адаптер только обновляет данные и зовёт
         *      notifyItemRemoved — DefaultItemAnimator делает плавный
         *      fade + сдвиг соседей за 250ms (Material-стандарт, как
         *      в Gmail/Telegram).
         *
         * Защита от двойного нажатия: deleteFavouriteWagonBtn.isEnabled = false
         * блокирует кнопку до полного удаления, isEnabled = true в bindView
         * восстанавливает кнопку при перевязке ViewHolder'а.
         */
        private fun animateAndDelete(wagonsFavourite: WagonsFavourite) {
            @Suppress("DEPRECATION")
            val pos = adapterPosition
            if (pos == RecyclerView.NO_POSITION) return

            binding.deleteFavouriteWagonBtn.isEnabled = false

            // БД-удаление — параллельно с анимацией ItemAnimator'а
            listener.onDeleteFavourite(wagonsFavourite)

            if (pos < wagonsFavourites.size) {
                wagonsFavourites.removeAt(pos)
                notifyItemRemoved(pos)
            }

            if (wagonsFavourites.isEmpty()) {
                listener.notFavourites()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ItemWagonFavouriteBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(wagonsFavourites[position])
    }

    override fun getItemCount(): Int = wagonsFavourites.size

    interface OnClickListener {
        fun onDeleteFavourite(wagonsFavourite: WagonsFavourite)
        fun notFavourites()
        fun onClickFavourite(wagonsFavourite: WagonsFavourite)  // NEW
    }
}