package com.hfad.smgrapp.ui.smgr

import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.transition.ChangeBounds
import androidx.transition.ChangeImageTransform
import androidx.transition.TransitionManager
import androidx.transition.TransitionSet
import com.hfad.smgrapp.App
import com.hfad.smgrapp.R
import com.hfad.smgrapp.dao.WagonsDao
import com.hfad.smgrapp.databinding.FragmentParameterWagonBinding
import com.hfad.smgrapp.model.Wagons
import com.hfad.smgrapp.model.WagonsFavourite
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ParameterWagonFragment(var wagons: Wagons) : Fragment() {

    private lateinit var binding: FragmentParameterWagonBinding
    private lateinit var appDao: WagonsDao

    private var isPhotoZoomed = false

    // Высоты для двух состояний, в dp.
    private val collapsedHeightDp = 180
    private val zoomedHeightDp = 320

    /**
     * NEW — текущий объект "избранного" для этого вагона.
     * null  → вагона нет в избранном (кнопка показывает контур сердца, клик добавит).
     * не-null → вагон в избранном (кнопка показывает заполненное сердце, клик удалит).
     * Хранит объект с реальным id из БД, поэтому его можно сразу передать в deleteWagon().
     */
    private var currentFavourite: WagonsFavourite? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentParameterWagonBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        appDao = (context?.applicationContext as App).getDatabase().wagonsDao()

        with(binding) {

            toolbar.textView.text = "Параметры вагона"

            toolbar.clickBackBtn.setOnClickListener {
                (requireActivity() as WagonActivity).onBackPressed()
            }

            // CHANGED — клик по сердцу теперь работает как toggle:
            // нет в избранном → добавить; есть в избранном → удалить.
            toolbar.clickHomeBtn.setOnClickListener {
                toggleFavourite()
            }

            // Клик по картинке — переключение зума
            wagonPhotoUrl.setOnClickListener { togglePhotoZoom() }

            // Стартовая проверка: есть ли вагон в избранном?
            refreshFavouriteState()

            // Фото вагона
            if (wagons.photoURL.isEmpty()) {
                wagonPhotoUrl.setImageResource(R.drawable.no_image_wagon)
            } else {
                Picasso.get().load(wagons.photoURL).into(wagonPhotoUrl)
            }

            // ── Параметры ────────────────────────────────────────────────────
            // Все поля проходят через setParamValue(): пустые значения и "н.д."
            // автоматически подсвечиваются warning-цветом.
            wagonModel.setParamValue(wagons.model)
            wagonProperty.setParamValue(wagons.property)
            wagonSpecialization.setParamValue(wagons.specialization)
            wagonMaterial.setParamValue(wagons.material)
            wagonFactory.setParamValue(wagons.factory)
            wagonCapacity.setParamValue(wagons.capacity)
            wagonTareMin.setParamValue(wagons.tareMin)
            wagonTareMax.setParamValue(wagons.tareMax)
            wagonTareMinExp.setParamValue(wagons.tareMinExp)
            wagonLength.setParamValue(wagons.length)
            wagonNumAxles.setParamValue(wagons.numAxles)
            wagonAxialLoad.setParamValue(wagons.axialLoad)
            wagonVolume.setParamValue(wagons.volume)
            wagonBogie.setParamValue(wagons.bogie)
            wagonSize.setParamValue(wagons.size)
            wagonYearOfRelease.setParamValue(wagons.yearOfRelease)
            wagonYearEndOfRelease.setParamValue(wagons.yearEndOfRelease)
            wagonServiceLife.setParamValue(wagons.serviceLife)
            wagonLong.setParamValue(wagons.long)
        }
    }

    // ── Избранное ────────────────────────────────────────────────────────────

    /**
     * NEW — читает состояние из БД и обновляет UI.
     * Вызывается при старте фрагмента.
     */
    private fun refreshFavouriteState() {
        lifecycleScope.launch(Dispatchers.IO) {
            @Suppress("SENSELESS_COMPARISON")
            // Room может вернуть null несмотря на non-null Kotlin-тип.
            val existing: WagonsFavourite? = appDao.getWagonFavorite(wagons.modelCode)
            withContext(Dispatchers.Main) {
                currentFavourite = existing
                updateFavouriteIcon()
            }
        }
    }

    /**
     * NEW — toggle избранного по клику на сердце.
     * • Если вагона в избранном нет → вставляем, потом перечитываем объект из БД
     *   (чтобы получить реальный autoGenerated id для будущего удаления).
     * • Если вагон в избранном → удаляем по сохранённому объекту, обнуляем состояние.
     */
    private fun toggleFavourite() {
        val snapshot = currentFavourite
        if (snapshot == null) {
            // ── Добавление ────────────────────────────────────────────────────
            val toInsert = WagonsFavourite(
                0,
                wagons.modelCode,
                wagons.model,
                wagons.photoURL,
                wagons.rod,
                wagons.yearOfRelease,
                wagons.yearEndOfRelease,
                wagons.capacity,
                wagons.property,
                wagons.specialization,
                wagons.material,
                wagons.factory,
                wagons.tareMin,
                wagons.tareMax,
                wagons.tareMinExp,
                wagons.boltedConnection,
                wagons.length,
                wagons.numAxles,
                wagons.axialLoad,
                wagons.footbridge,
                wagons.volume,
                wagons.calibration,
                wagons.bogie,
                wagons.size,
                wagons.serviceLife
            )
            lifecycleScope.launch(Dispatchers.IO) {
                appDao.insertWagon(toInsert)
                // Перечитываем, чтобы получить объект с реальным id.
                @Suppress("SENSELESS_COMPARISON")
                val inserted: WagonsFavourite? =
                    appDao.getWagonFavorite(wagons.modelCode)
                withContext(Dispatchers.Main) {
                    currentFavourite = inserted
                    updateFavouriteIcon()
                    Toast.makeText(
                        context,
                        "Добавлен в избранное",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        } else {
            // ── Удаление ──────────────────────────────────────────────────────
            lifecycleScope.launch(Dispatchers.IO) {
                appDao.deleteWagon(snapshot)
                withContext(Dispatchers.Main) {
                    currentFavourite = null
                    updateFavouriteIcon()
                    Toast.makeText(
                        context,
                        "Удалён из избранного",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    /**
     * NEW — синхронизирует иконку сердца с currentFavourite.
     * Источник истины — поле currentFavourite, не сама иконка. Это избавляет
     * от "мерцания" и рассинхрона, которые были в старом коде.
     */
    private fun updateFavouriteIcon() {
        val iconRes = if (currentFavourite != null) {
            R.drawable.ic_baseline_favorite_24     // заполненное
        } else {
            R.drawable.ic_baseline_favorite_border // контур
        }
        binding.toolbar.clickHomeBtn.setImageResource(iconRes)
    }

    // ── Утилита: значения параметров ─────────────────────────────────────────

    /**
     * Устанавливает значение параметра с авто-подсветкой пустых данных.
     *
     * Если значение пустое или равно "н.д." — текст "н.д." окрашивается в
     * @color/warning как визуальный маркер "требует заполнения" (принцип
     * TODO-подсветки в IDE). Для валидных данных — дефолтный цвет темы.
     */
    private fun TextView.setParamValue(value: String?) {
        val isEmpty = value.isNullOrBlank() ||
                value.trim().equals("н.д.", ignoreCase = true)

        text = if (isEmpty) "н.д." else value

        val colorInt = if (isEmpty) {
            ContextCompat.getColor(context, R.color.warning)
        } else {
            val tv = TypedValue()
            context.theme.resolveAttribute(
                com.google.android.material.R.attr.colorOnSurface, tv, true
            )
            tv.data
        }
        setTextColor(colorInt)
    }

    // ── Зум фото ─────────────────────────────────────────────────────────────

    /**
     * Свёрнутое: фикс. высота, ширина match_parent, fitCenter — картинка вписана целиком.
     * Развёрнутое: увеличенная высота, ширина wrap_content + adjustViewBounds —
     * картинка вылезает за экран по ширине, HorizontalScrollView даёт горизонтальный скролл.
     */
    private fun togglePhotoZoom() = with(binding) {
        isPhotoZoomed = !isPhotoZoomed

        TransitionManager.beginDelayedTransition(
            imageScrollView,
            TransitionSet()
                .addTransition(ChangeBounds())
                .addTransition(ChangeImageTransform())
        )

        val density = resources.displayMetrics.density

        if (isPhotoZoomed) {
            wagonPhotoUrl.adjustViewBounds = true
            wagonPhotoUrl.updateLayoutParams {
                width = ViewGroup.LayoutParams.WRAP_CONTENT
                height = (zoomedHeightDp * density).toInt()
            }
            wagonPhotoUrl.scaleType = ImageView.ScaleType.FIT_CENTER
        } else {
            wagonPhotoUrl.adjustViewBounds = false
            wagonPhotoUrl.updateLayoutParams {
                width = ViewGroup.LayoutParams.MATCH_PARENT
                height = (collapsedHeightDp * density).toInt()
            }
            wagonPhotoUrl.scaleType = ImageView.ScaleType.FIT_CENTER
            imageScrollView.smoothScrollTo(0, 0)
        }
    }
}