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
    lateinit var appDao: WagonsDao

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

            toolbar.clickHomeBtn.setImageDrawable(resources.getDrawable(R.drawable.ic_baseline_favorite_border))

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
                toolbar.clickHomeBtn.setImageDrawable(resources.getDrawable(R.drawable.ic_baseline_favorite_24))

                Toast.makeText(context, "Добавлен в избранное", Toast.LENGTH_SHORT).show()
            }

            var isFullScreen = false

            wagonPhotoUrl.setOnClickListener {
                isFullScreen = isFullScreen.not()
                TransitionManager.beginDelayedTransition(
                    imageIdContainer,
                    TransitionSet()
                        .addTransition(ChangeBounds())
                        .addTransition(ChangeImageTransform())
                )
                val dimensionWidth = if(isFullScreen) ViewGroup.LayoutParams.WRAP_CONTENT else resources.getDimension(R.dimen.frag_param_wagon_image_width).toInt()
                val dimensionHeight = if(isFullScreen) ViewGroup.LayoutParams.WRAP_CONTENT else resources.getDimension(R.dimen.frag_param_wagon_image_height).toInt()

                val scaleType = if(isFullScreen) ImageView.ScaleType.CENTER else ImageView.ScaleType.CENTER_INSIDE

                wagonPhotoUrl.updateLayoutParams {
                    width = dimensionWidth
                    height = dimensionHeight
                }

                wagonPhotoUrl.scaleType = scaleType
            }

            lifecycleScope.launch(Dispatchers.IO) {
                val wagonFav = appDao.getWagonFavorite(wagons.modelCode)

                wagonFav.let { a ->
                    withContext(Dispatchers.Main) {
                        @Suppress("SENSELESS_COMPARISON")
                        toolbar.clickHomeBtn.isClickable = a == null
                        if (toolbar.clickHomeBtn.isClickable) {
                            toolbar.clickHomeBtn.setImageDrawable(resources.getDrawable(R.drawable.ic_baseline_favorite_border))
                        } else {
                            toolbar.clickHomeBtn.setImageDrawable(resources.getDrawable(R.drawable.ic_baseline_favorite_24))
                        }
                    }
                }
            }



            if (wagons.photoURL.isEmpty()) {
                wagonPhotoUrl.setImageResource(R.drawable.no_image_wagon)
            } else {
                Picasso.get().load(wagons.photoURL).into(wagonPhotoUrl)
            }
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
}