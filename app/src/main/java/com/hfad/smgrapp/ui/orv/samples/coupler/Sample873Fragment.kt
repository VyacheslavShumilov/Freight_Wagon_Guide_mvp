package com.hfad.smgrapp.ui.orv.samples.coupler

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.MediaController
import com.hfad.smgrapp.R
import com.hfad.smgrapp.databinding.FragmentBogieBinding
import com.hfad.smgrapp.databinding.FragmentSample873Binding


class Sample873Fragment : Fragment() {

    private lateinit var binding: FragmentSample873Binding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSample873Binding.inflate(inflater, container, false)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mediaController = MediaController(requireActivity())
        mediaController.setAnchorView(binding.videoView)

        val offlineUri = Uri.parse("android.resource://"+ activity?.packageName + "/" + R.raw.sample_873)

        with(binding) {
            videoView.setOnClickListener {
                videoView.setMediaController(mediaController)
                videoView.setVideoURI(offlineUri)
                videoView.requestFocus()
                videoView.start()
            }
        }
    }
}