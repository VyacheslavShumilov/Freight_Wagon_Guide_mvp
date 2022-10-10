package com.hfad.smgrapp.ui.orv

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hfad.smgrapp.databinding.FragmentAutomaticCouplerBinding

class AutomaticCouplerFragment : Fragment() {

    private lateinit var binding: FragmentAutomaticCouplerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAutomaticCouplerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {

            toolbar.textView.text = "Автосцепка"

            toolbar.clickBackBtn.setOnClickListener{
                (requireActivity() as OrvActivity).onBackPressed()
            }

            toolbar.clickHomeBtn.setOnClickListener {
                (requireActivity() as OrvActivity).finish()
            }
        }
    }
}