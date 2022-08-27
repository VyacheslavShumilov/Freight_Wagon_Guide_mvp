package com.hfad.smgrapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hfad.smgrapp.databinding.FragmentComponentsBinding


class ComponentsFragment : Fragment() {
    private lateinit var binding: FragmentComponentsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentComponentsBinding.inflate(inflater, container, false)
        return binding.root
    }


}