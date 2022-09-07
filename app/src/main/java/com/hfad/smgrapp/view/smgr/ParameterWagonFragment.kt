package com.hfad.smgrapp.view.smgr

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.hfad.smgrapp.R
import com.hfad.smgrapp.databinding.FragmentParameterWagonBinding
import com.hfad.smgrapp.model.Wagons
import com.squareup.picasso.Picasso


class ParameterWagonFragment(var wagons: Wagons) : Fragment() {

    private lateinit var binding: FragmentParameterWagonBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentParameterWagonBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {

            toolbar.textView.text = "Параметры вагона"

            toolbar.clickBackBtn.setOnClickListener{
                (requireActivity() as WagonActivity).onBackPressed()
            }

            toolbar.clickHomeBtn.visibility = View.GONE


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
            wagonYearOfRelease.text = wagons.yearOfRelease
            wagonYearEndOfRelease.text = wagons.yearEndOfRelease
            wagonServiceLife.text = wagons.serviceLife
            wagonLong.text = wagons.long
        }

    }
}