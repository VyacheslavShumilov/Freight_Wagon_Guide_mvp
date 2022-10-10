package com.hfad.smgrapp.ui.orv.bogies

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hfad.smgrapp.App
import com.hfad.smgrapp.ui.orv.bogies.adapter.AdapterBogies
import com.hfad.smgrapp.ui.orv.bogies.impl.BogiesListContract
import com.hfad.smgrapp.ui.orv.bogies.impl.BogiesListPresenterImpl
import com.hfad.smgrapp.databinding.FragmentComponentsBinding
import com.hfad.smgrapp.model.Bogies
import com.hfad.smgrapp.navigator.AppNavigatorParam
import com.hfad.smgrapp.navigator.ScreenParam
import com.hfad.smgrapp.ui.orv.OrvActivity


class BogiesListFragment : Fragment(), BogiesListContract.View, AdapterBogies.SetOnClickListener {
    private lateinit var binding: FragmentComponentsBinding
    private lateinit var presenter: BogiesListPresenterImpl
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
        presenter = BogiesListPresenterImpl()
        presenter.attachView(this)
        presenter.responseData()

        with(binding) {

            toolbar.textView.text = "Модели тележек 23,5 т/ось"

            toolbar.clickBackBtn.setOnClickListener{
                (requireActivity() as OrvActivity).onBackPressed()
            }

            toolbar.clickHomeBtn.setOnClickListener {
                (requireActivity() as OrvActivity).finish()
            }
        }
    }

    override fun onSuccessList(bogies: ArrayList<Bogies>) {
        val adapterBogies = AdapterBogies(bogies, this)
        binding.recyclerView.adapter = adapterBogies
    }

    override fun error(errMessage: String) {
        binding.layoutNotConnection.visibility = View.VISIBLE
        binding.btnClickReply.setOnClickListener {
            presenter.responseData()
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