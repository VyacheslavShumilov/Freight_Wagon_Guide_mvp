package com.hfad.smgrapp.view.orv.components


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hfad.smgrapp.adapter.AdapterBogieComponents
import com.hfad.smgrapp.controller.BogiesComponentsController
import com.hfad.smgrapp.databinding.FragmentBogieComponentsBinding
import com.hfad.smgrapp.model.BogiesComponents
import com.hfad.smgrapp.view.orv.OrvActivity

class BogieComponentsFragment(var modelBogie: String) : Fragment(), IBogiesComponentsView {
    private lateinit var binding: FragmentBogieComponentsBinding
    private var bogiesComponentsController: BogiesComponentsController? = null
    private var adapterBogiesComponents = AdapterBogieComponents()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBogieComponentsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bogiesComponentsController = BogiesComponentsController(this)
        (bogiesComponentsController as BogiesComponentsController).onBogiesComponentsList()

        with(binding) {

            toolbar.textView.text = "Модель: $modelBogie"

            toolbar.clickBackBtn.setOnClickListener{
                (requireActivity() as OrvActivity).onBackPressed()
            }

            toolbar.clickHomeBtn.setOnClickListener {
                (requireActivity() as OrvActivity).finish()
            }
        }
    }

    override fun onSuccessList(bogiesComponents: ArrayList<BogiesComponents>) {
        for (i in bogiesComponents) {
            if (i.modelBogie == modelBogie) {
                adapterBogiesComponents.addItem(i)
                adapterBogiesComponents.notifyDataSetChanged()
            }
        }
        binding.recyclerView.adapter = adapterBogiesComponents
    }

    override fun error(errMessage: String) {
        binding.layoutNotConnection.visibility = View.VISIBLE
        binding.btnClickReply.setOnClickListener {
            (bogiesComponentsController as BogiesComponentsController).onBogiesComponentsList()
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


}