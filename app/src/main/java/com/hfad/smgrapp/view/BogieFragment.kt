package com.hfad.smgrapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hfad.smgrapp.databinding.FragmentBogieBinding


class BogieFragment : Fragment() {
    private lateinit var binding: FragmentBogieBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBogieBinding.inflate(inflater, container, false)
        return binding.root
    }


}