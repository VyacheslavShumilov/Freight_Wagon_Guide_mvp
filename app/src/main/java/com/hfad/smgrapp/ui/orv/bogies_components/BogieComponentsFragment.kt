package com.hfad.smgrapp.ui.orv.bogies_components


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hfad.smgrapp.ui.orv.bogies_components.adapter.AdapterBogieComponents
import com.hfad.smgrapp.ui.orv.bogies_components.impl.BogiesComponentsContract
import com.hfad.smgrapp.ui.orv.bogies_components.impl.BogiesComponentsPresenterImpl
import com.hfad.smgrapp.databinding.FragmentBogieComponentsBinding
import com.hfad.smgrapp.model.BogiesComponents
import com.hfad.smgrapp.ui.orv.OrvActivity

class BogieComponentsFragment(var modelBogie: String) : Fragment(), BogiesComponentsContract.View {

    private lateinit var binding: FragmentBogieComponentsBinding
    private lateinit var presenter: BogiesComponentsPresenterImpl
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

        presenter = BogiesComponentsPresenterImpl()
        presenter.attachView(this)
        presenter.responseData()

        with(binding) {

            toolbar.textView.text = "Модель: $modelBogie"

            toolbar.clickBackBtn.setOnClickListener {
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


}