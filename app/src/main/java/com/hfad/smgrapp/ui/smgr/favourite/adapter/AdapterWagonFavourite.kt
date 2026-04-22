package com.hfad.smgrapp.ui.smgr.favourite.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
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

                // Сбрасываем трансформации — ViewHolder мог быть переиспользован
                // после анимации удаления предыдущей карточки.
                itemView.alpha = 1f
                itemView.scaleX = 1f
                itemView.scaleY = 1f
                deleteFavouriteWagonBtn.isEnabled = true

                deleteFavouriteWagonBtn.setOnClickListener {
                    animateAndDelete(wagonsFavourite)
                }
            }
        }

        /**
         * Корректная анимация удаления:
         *  1. Сразу удаляем из БД (IO-запрос в activity).
         *  2. Блокируем кнопку от повторных нажатий.
         *  3. Анимируем карточку: fade + scale down (220ms, Material-стандарт).
         *  4. По завершении — notifyItemRemoved(pos): это активирует
         *     DefaultItemAnimator, который плавно сдвинет оставшиеся карточки.
         *  5. Сбрасываем трансформации ViewHolder'а для безопасного
         *     переиспользования в будущем.
         *
         * FIXED — используется adapterPosition вместо bindingAdapterPosition:
         * последний доступен только с RecyclerView 1.2.0+. Для этого проекта
         * (один простой адаптер без ConcatAdapter) семантика идентична.
         */
        private fun animateAndDelete(wagonsFavourite: WagonsFavourite) {
            @Suppress("DEPRECATION")
            val pos = adapterPosition
            if (pos == RecyclerView.NO_POSITION) return

            binding.deleteFavouriteWagonBtn.isEnabled = false

            // БД-удаление параллельно анимации
            listener.onDeleteFavourite(wagonsFavourite)

            itemView.animate()
                .alpha(0f)
                .scaleX(0.85f)
                .scaleY(0.85f)
                .setDuration(220L)
                .setInterpolator(AccelerateDecelerateInterpolator())
                .withEndAction {
                    @Suppress("DEPRECATION")
                    val current = adapterPosition

                    // Сброс трансформаций — обязательно ПЕРЕД notifyItemRemoved,
                    // чтобы освобождаемый ViewHolder не ушёл в пул "битым".
                    itemView.alpha = 1f
                    itemView.scaleX = 1f
                    itemView.scaleY = 1f
                    binding.deleteFavouriteWagonBtn.isEnabled = true

                    if (current != RecyclerView.NO_POSITION &&
                        current < wagonsFavourites.size
                    ) {
                        wagonsFavourites.removeAt(current)
                        notifyItemRemoved(current)
                    }

                    if (wagonsFavourites.isEmpty()) {
                        listener.notFavourites()
                    }
                }
                .start()
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
    }
}