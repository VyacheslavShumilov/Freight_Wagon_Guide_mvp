package com.hfad.smgrapp.view.Orv


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hfad.smgrapp.databinding.FragmentBogieComponentsBinding

class BogieComponentsFragment : Fragment() {
    private lateinit var binding: FragmentBogieComponentsBinding




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBogieComponentsBinding.inflate(inflater, container, false)
        return binding.root
    }



}