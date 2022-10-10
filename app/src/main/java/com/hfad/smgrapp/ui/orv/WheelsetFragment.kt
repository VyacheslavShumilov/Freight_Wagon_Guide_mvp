package com.hfad.smgrapp.ui.orv

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hfad.smgrapp.databinding.FragmentWheelsetBinding


class WheelsetFragment : Fragment() {
    private lateinit var binding: FragmentWheelsetBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWheelsetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {

            toolbar.textView.text = "Колесная пара"

            toolbar.clickBackBtn.setOnClickListener{
                (requireActivity() as OrvActivity).onBackPressed()
            }

            toolbar.clickHomeBtn.setOnClickListener {
                (requireActivity() as OrvActivity).finish()
            }
        }
    }
}