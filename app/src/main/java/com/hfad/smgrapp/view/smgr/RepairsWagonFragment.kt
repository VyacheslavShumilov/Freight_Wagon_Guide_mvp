package com.hfad.smgrapp.view.smgr

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hfad.smgrapp.R
import com.hfad.smgrapp.databinding.FragmentRepairsWagonBinding
import com.hfad.smgrapp.model.Wagons

class RepairsWagonFragment(var wagons: Wagons) : Fragment() {
    private lateinit var binding: FragmentRepairsWagonBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRepairsWagonBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {

            toolbar.textView.text = "Периодичность ремонтов"

            toolbar.clickBackBtn.setOnClickListener{
                (requireActivity() as WagonActivity).onBackPressed()
            }

            toolbar.clickHomeBtn.visibility = View.GONE

            wagonModel.text = wagons.model
            textDrAftRelease.text = wagons.drAftRelease
            textDrAftDrTo1kr.text = wagons.drAftDrTo1Kr
            textDrAftDrAft1Kr.text = wagons.drAftDraft1Kr
            textDrAftKr.text = wagons.drAftKr
            textKrAftRelease.text = wagons.krAftRelease
            textKrAftKr.text = wagons.krAftKr
            textDrAftReleaseRepProbKm.text = wagons.drAftReleaseRepProbKm
            textDrAftReleaseRepYears.text = wagons.drAftReleaseRepYears
            textDrAftDrRepProbKm.text = wagons.drAftDrRepProbKm
            textDrAftDrRepProbYears.text = wagons.drAftDrRepProbYears
            textDrAftKrRepProbKm.text = wagons.drAftKrRepProbKm
            textDrAftKrRepYears.text = wagons.drAftKrRepProbYears
            textDrAftKrpRepProbKm.text = wagons.drAftKrpRepProbKm
            textDrAftKrpRepProbYears.text = wagons.drAftKrpRepProbYears
            textContinueTu.text = wagons.continueTu
            textDrAftKrpTu.text = wagons.drAftKrpTu
            textKrAftKrpTu.text = wagons.krAftKrpTu
        }
    }
}