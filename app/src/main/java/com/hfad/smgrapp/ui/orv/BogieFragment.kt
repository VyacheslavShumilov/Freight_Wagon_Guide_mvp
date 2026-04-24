package com.hfad.smgrapp.ui.orv

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hfad.smgrapp.R
import com.hfad.smgrapp.databinding.FragmentBogieBinding

class BogieFragment : Fragment() {
    private lateinit var binding: FragmentBogieBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBogieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {

            toolbar.textView.text = "Тележка"

            toolbar.clickBackBtn.setOnClickListener{
                (requireActivity() as OrvActivity).onBackPressed()
            }

            toolbar.clickHomeBtn.setOnClickListener {
                (requireActivity() as OrvActivity).finish()
            }
            toolbar.clickHomeBtn.setImageResource(R.drawable.ic_baseline_home)
// если в drawable уже есть fill=white — tint применится из stylesheet'а toolbar.xml
// (app:tint="?attr/colorOnSurface")
        }
    }
}