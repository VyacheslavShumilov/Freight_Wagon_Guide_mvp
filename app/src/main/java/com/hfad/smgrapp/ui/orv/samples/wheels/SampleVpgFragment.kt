package com.hfad.smgrapp.ui.orv.samples.wheels

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import com.hfad.smgrapp.R
import com.hfad.smgrapp.databinding.FragmentSample873Binding
import com.hfad.smgrapp.databinding.FragmentSampleVpgBinding


class SampleVpgFragment : Fragment() {

    private lateinit var binding: FragmentSampleVpgBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSampleVpgBinding.inflate(inflater, container, false)
        return binding.root    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mediaController = MediaController(requireActivity())
        mediaController.setAnchorView(binding.videoView)

        val offlineUri = Uri.parse("android.resource://"+ activity?.packageName + "/" + R.raw.sample_vpg)

        with(binding) {

            playBtn.setOnClickListener {
                playBtn.visibility = View.GONE
                videoView.setMediaController(mediaController)
                videoView.setVideoURI(offlineUri)
                videoView.requestFocus()
                videoView.start()
            }
        }
    }
}