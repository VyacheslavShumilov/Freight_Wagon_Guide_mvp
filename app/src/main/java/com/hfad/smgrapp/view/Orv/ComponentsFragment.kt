package com.hfad.smgrapp.view.Orv

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hfad.smgrapp.App
import com.hfad.smgrapp.adapter.AdapterBogies
import com.hfad.smgrapp.controller.BogiesController
import com.hfad.smgrapp.controller.IBogiesController
import com.hfad.smgrapp.databinding.FragmentComponentsBinding
import com.hfad.smgrapp.model.Bogies
import com.hfad.smgrapp.navigator.AppNavigator
import com.hfad.smgrapp.navigator.AppNavigatorParam
import com.hfad.smgrapp.navigator.Screen
import com.hfad.smgrapp.navigator.ScreenParam


class ComponentsFragment : Fragment(), IBogiesView, AdapterBogies.SetOnClickListener {
    private lateinit var binding: FragmentComponentsBinding
    private var bogiesController: IBogiesController? = null
    private lateinit var appNavigatorParam: AppNavigatorParam

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentComponentsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bogiesController = BogiesController(this)
        (bogiesController as BogiesController).onBogiesList()
    }

    override fun onSuccessList(bogies: ArrayList<Bogies>) {
        val adapterBogies = AdapterBogies(bogies, this)
        binding.recyclerView.adapter = adapterBogies
    }

    override fun error(errMessage: String) {
        binding.layoutNotConnection.visibility = View.VISIBLE
        binding.btnClickReply.setOnClickListener {
            (bogiesController as BogiesController).onBogiesList()
        }
    }

    override fun progress(show: Boolean) {
        if (show) {
            binding.layoutNotConnection.visibility = View.GONE
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.layoutNotConnection.visibility = View.GONE
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onClickBogie(modelBogie: String) {
        appNavigatorParam.navigateToParam(ScreenParam.COMPONENTS_BOGIE_INFO, modelBogie)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        appNavigatorParam = (context.applicationContext as App).servicesLocator.providerNavigatorParam(requireActivity())
    }
}