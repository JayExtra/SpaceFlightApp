package com.dev.james.launchlibraryapi.features.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.dev.james.launchlibraryapi.R
import com.dev.james.launchlibraryapi.databinding.LaunchDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LaunchDetailsFragment : Fragment(R.layout.launch_details) {

    private lateinit var binding : LaunchDetailsBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = LaunchDetailsBinding.bind(view)

        setUpToolbar()

    }

    private fun setUpToolbar() {
        val toolbar = binding.launchDetailsToolbar as Toolbar
        toolbar.elevation = 0.0F
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar!!.title = "Launch slug"
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar!!.setHomeButtonEnabled(true)

        toolbar.setNavigationOnClickListener {
            binding.root.findNavController().popBackStack()
        }

    }

}