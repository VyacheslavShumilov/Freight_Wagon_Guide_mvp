package com.hfad.smgrapp.view.Orv

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hfad.smgrapp.databinding.FragmentBreakSystemBinding


class BreakSystemFragment : Fragment() {
    private lateinit var binding: FragmentBreakSystemBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBreakSystemBinding.inflate(inflater, container, false)
        return binding.root
    }
}