package com.dev.james.launchlibraryapi.features.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.dev.james.launchlibraryapi.R
import com.dev.james.launchlibraryapi.databinding.FragmentRocketConfigBinding
import com.dev.james.launchlibraryapi.features.viewmodels.LaunchListViewModel
import com.dev.james.launchlibraryapi.utils.NetworkResource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RocketFragment : Fragment(R.layout.fragment_rocket_config) {

    private lateinit var binding : FragmentRocketConfigBinding
    private val viewModel : LaunchListViewModel by activityViewModels()
    private val args = RocketFragmentArgs

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRocketConfigBinding.bind(view)

        val arguments  = arguments?.let {
            args.fromBundle(it)
        }

        val rocketId = arguments?.rocketId

        binding.backArrow.setOnClickListener{
            findNavController().popBackStack()
        }


        getRocketInstance(rocketId)
        observeRocketResponse()
    }

    private fun getRocketInstance(rocketId: Int?) {
        rocketId?.let { viewModel.getRocket(it) }
    }

    private fun observeRocketResponse() {
        viewModel.rocketResponse.observe(viewLifecycleOwner , { event ->
            event.getContentIfNotHandled()?.let { response ->
                when(response){
                    is NetworkResource.Loading -> {
                        binding.apply {
                            progressRocketImage.isVisible = true
                            statsCard.isVisible = false
                            rocketNameTxt.isInvisible = true
                            manufacturersTxt.isInvisible = true
                            comGovTxt.isInvisible = true
                        }

                    }

                    is NetworkResource.Success -> {
                        val rocket = response.value
                        binding.apply {
                            progressRocketImage.isInvisible = true
                            statsCard.isVisible = true
                            rocketNameTxt.isInvisible = false
                            manufacturersTxt.isInvisible = false
                            comGovTxt.isInvisible = false

                            rocketNameTxt.text = rocket.name
                            manufacturersTxt.text = rocket.manufacturer.name
                            comGovTxt.text = rocket.manufacturer.type

                            //load statistics
                            countryCodeTxt.text = rocket.manufacturer.country
                            variantTxt.text = rocket.variant
                            familyTxt.text = rocket.family
                            diameterTxt.text = "${rocket.diameter} m"
                            heightTxt.text = "${rocket.length} m"
                            apogeeTxt.text = "${rocket.apogee} m"
                            toThrustTxt.text = "${rocket.toThrust} N"
                            maxStageTxt.text = rocket.maxStage.toString()
                            minStageTxt.text = rocket.minStage.toString()
                            capGsoTxt.text = "${rocket.gtoCapacity} kg"
                            capLeoTxt.text = "${rocket.leoCapacity} kg"
                            tLaunchesTxt.text = rocket.totalLaunches.toString()
                            sLaunchesTxt.text = rocket.successfulLaunches.toString()
                            fLaunchesTxt.text = rocket.failedLaunches.toString()

                        }
                        loadImage(rocket.image)

                    }

                    is NetworkResource.Failure -> {
                        Toast.makeText(requireContext(), response.errBody.toString(), Toast.LENGTH_SHORT).show()
                    }
                }

            }
        })
    }

    private fun loadImage(image: String) {
        Glide.with(binding.root)
            .load(image)
            .transition(DrawableTransitionOptions.withCrossFade())
            .centerCrop()
            .placeholder(R.drawable.rocket)
            .into(binding.rocketImage)
    }
}