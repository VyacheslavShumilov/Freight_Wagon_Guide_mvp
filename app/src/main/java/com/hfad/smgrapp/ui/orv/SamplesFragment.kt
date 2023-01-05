package com.hfad.smgrapp.ui.orv

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.hfad.smgrapp.App
import com.hfad.smgrapp.databinding.FragmentSamplesBinding
import com.hfad.smgrapp.navigator.AppNavigator
import com.hfad.smgrapp.navigator.Screen

class SamplesFragment: Fragment() {
    private lateinit var binding: FragmentSamplesBinding
    private lateinit var appNavigator: AppNavigator

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSamplesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        appNavigator = (context.applicationContext as App).servicesLocator.providerNavigator(requireActivity())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            toolbar.textView.text = "Шаблоны"

            toolbar.clickBackBtn.setOnClickListener{
                (requireActivity() as OrvActivity).onBackPressed()
            }

            toolbar.clickHomeBtn.setOnClickListener {
                (requireActivity() as OrvActivity).finish()
            }

            cardViewAbs.setOnClickListener {
               appNavigator.navigateTo(Screen.SAMPLE_ABS)
            }

            cardViewVpg.setOnClickListener {
                appNavigator.navigateTo(Screen.SAMPLE_VPG)
            }

        }
    }
}