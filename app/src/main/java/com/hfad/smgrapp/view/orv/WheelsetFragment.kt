package com.hfad.smgrapp.view.orv

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


}