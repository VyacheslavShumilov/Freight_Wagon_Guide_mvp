package com.hfad.smgrapp.view.Orv

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hfad.smgrapp.App
import com.hfad.smgrapp.databinding.FragmentOrvListBinding
import com.hfad.smgrapp.navigator.AppNavigator
import com.hfad.smgrapp.navigator.Screen


class OrvListFragment : Fragment() {
    private lateinit var binding: FragmentOrvListBinding
    private lateinit var appNavigator: AppNavigator

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOrvListBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        appNavigator = (context.applicationContext as App).servicesLocator.providerNavigator(requireActivity())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            cardViewAutomaticCoupler.setOnClickListener {
                appNavigator.navigateTo(Screen.AUTOMATIC_COUPLER)
            }

            cardViewBreakSystem.setOnClickListener {
                appNavigator.navigateTo(Screen.BREAK_SYSTEM)
            }
            cardViewWheelset.setOnClickListener {
                appNavigator.navigateTo(Screen.WHEELSET)
            }
            cardViewBogie.setOnClickListener {
                appNavigator.navigateTo(Screen.BOGIE)
            }
            cardViewComponents.setOnClickListener {
                appNavigator.navigateTo(Screen.COMPONENTS)
            }

        }
    }
}