package com.hfad.smgrapp.ui.smgr

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
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

            toolbar.clickHomeBtn.setImageDrawable(
                resources.getDrawable(R.drawable.ic_baseline_favorite_border)
            )

            toolbar.clickHomeBtn.setOnClickListener {
                lifecycleScope.launch(Dispatchers.IO) {
                    val wagonFavourite = WagonsFavourite(
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
                    appDao.insertWagon(wagonFavourite)
                }
                toolbar.clickHomeBtn.isClickable = false
                toolbar.clickHomeBtn.setImageDrawable(
                    resources.getDrawable(R.drawable.ic_baseline_favorite_24)
                )

                Toast.makeText(context, "Добавлен в избранное", Toast.LENGTH_SHORT).show()
            }

            // Клик по картинке — переключение зума
            wagonPhotoUrl.setOnClickListener { togglePhotoZoom() }

            lifecycleScope.launch(Dispatchers.IO) {
                val wagonFav = appDao.getWagonFavorite(wagons.modelCode)

                wagonFav.let { a ->
                    withContext(Dispatchers.Main) {
                        @Suppress("SENSELESS_COMPARISON")
                        toolbar.clickHomeBtn.isClickable = a == null
                        if (toolbar.clickHomeBtn.isClickable) {
                            toolbar.clickHomeBtn.setImageDrawable(
                                resources.getDrawable(R.drawable.ic_baseline_favorite_border)
                            )
                        } else {
                            toolbar.clickHomeBtn.setImageDrawable(
                                resources.getDrawable(R.drawable.ic_baseline_favorite_24)
                            )
                        }
                    }
                }
            }

            // Фото вагона
            if (wagons.photoURL.isEmpty()) {
                wagonPhotoUrl.setImageResource(R.drawable.no_image_wagon)
            } else {
                Picasso.get().load(wagons.photoURL).into(wagonPhotoUrl)
            }

            // Параметры
            wagonModel.text = wagons.model
            wagonProperty.text = wagons.property
            wagonSpecialization.text = wagons.specialization
            wagonMaterial.text = wagons.material
            wagonFactory.text = wagons.factory
            wagonCapacity.text = wagons.capacity
            wagonTareMin.text = wagons.tareMin
            wagonTareMax.text = wagons.tareMax
            wagonTareMinExp.text = wagons.tareMinExp
            wagonLength.text = wagons.length
            wagonNumAxles.text = wagons.numAxles
            wagonAxialLoad.text = wagons.axialLoad
            wagonVolume.text = wagons.volume
            wagonBogie.text = wagons.bogie
            wagonSize.text = wagons.size
            wagonYearOfRelease.text = wagons.yearOfRelease
            wagonYearEndOfRelease.text = wagons.yearEndOfRelease
            wagonServiceLife.text = wagons.serviceLife
            wagonLong.text = wagons.long
        }
    }

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