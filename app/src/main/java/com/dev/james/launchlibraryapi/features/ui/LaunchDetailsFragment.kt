package com.dev.james.launchlibraryapi.features.ui

import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isGone
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.dev.james.launchlibraryapi.R
import com.dev.james.launchlibraryapi.databinding.LaunchDetailsBinding
import com.dev.james.launchlibraryapi.features.viewmodels.LaunchListViewModel
import com.dev.james.launchlibraryapi.models.Agency
import com.dev.james.launchlibraryapi.models.LaunchList
import com.dev.james.launchlibraryapi.utils.NetworkResource
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.math.roundToInt

@AndroidEntryPoint
class LaunchDetailsFragment : Fragment(R.layout.launch_details) {

    private lateinit var binding : LaunchDetailsBinding
    private val args = LaunchDetailsFragmentArgs
    var countDownTimer: CountDownTimer? = null
    private val viewModel : LaunchListViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = LaunchDetailsBinding.bind(view)

        val argument = arguments?.let { args.fromBundle(it)}
        val launchItem = argument?.singleLaunchResult
        val launchImage = argument?.launchImageUrl
        val launchName = argument?.launchName

        setUpToolbar(launchImage , launchName)
        setUpUi(launchItem)
        observeAgencyType()
        getAgency(launchItem)

    }

    private fun getAgency(launchItem: LaunchList?) {
        launchItem?.serviceProvider?.id?.let {
            //Toast.makeText(requireContext(), "id: $it ", Toast.LENGTH_LONG).show()
            viewModel.getAgency(it)
        }
    }

    private fun observeAgencyType() {
        viewModel.agencyResponse.observe(viewLifecycleOwner , { event ->
            event.getContentIfNotHandled()?.let { resource ->
                when(resource){
                    is NetworkResource.Loading -> {
                        binding.apply {
                            progressBarAgencyImage.isVisible = true
                        }
                    }
                    is NetworkResource.Success -> {

                        val agency = resource.value
                        binding.apply {
                            progressBarAgencyImage.isInvisible = true
                            totalLaunchesTxt.text = agency.totalLaunch.toString()
                            successfulLaunches.text = agency.successfulLaunches.toString()
                            failedLaunchesTxt.text = agency.failedLaunches.toString()
                            landingsTxt.text = agency.successfulLandings.toString()

                            if(agency.name.length > 20) {
                                agencyName.text = agency.abbrev
                            }else{
                                agencyName.text = agency.name
                            }

                            setUpAgencySuccesRateProgressBar(agency.successfulLaunches , agency.totalLaunch)


                        }
                        loadAgencyLogo(resource.value)
                    }
                    is NetworkResource.Failure ->{
                        Toast.makeText(requireContext(), resource.errBody.toString(), Toast.LENGTH_SHORT).show()
                    }
                }

            }
        })
    }

    private fun setUpAgencySuccesRateProgressBar(successfulLaunches: Int, totalLaunch: Int) {
        val percentage = calculateSuccessRate(totalLaunch, successfulLaunches).roundToInt()
        binding.apply {
            successRateBar.progress = percentage
            successRateTxt.text = "$percentage%"
        }
    }

    private fun loadAgencyLogo(value: Agency) {

        Log.d("DetailsFragment", "loadAgencyLogo: logo is ${value.logo}")
        Glide.with(binding.root)
            .load(value.logo)
            .placeholder(R.drawable.rocket)
            .centerCrop()
            .into(binding.agencyImage)
    }

    private fun setUpUi(launchItem: LaunchList?) {
        binding.apply {
            launchItem?.let {
                rocketTxt.text = it.rocket?.configuration?.name

                if(it.mission?.orbit!= null){
                    orbitTxt.text = it.mission.orbit.abbrev
                }else{
                    orbitTxt.text = "N/A"
                }

                locationTxt.text = it.pad.location.code
                statusDescriptionTxt.text = it.status?.description
                missionNameTxt.text = it.mission?.name
                missionDescription.text = it.mission?.description
                launchLocationText.text = it.pad.location.name
                launchPadNameTxt.text = it.pad.name
                totalLandingsCenter.text = it.pad.location.launchCount.toString()
                totalLaunchesPad.text = it.pad.launchCount.toString()
                totalLandings.text = it.pad.location.landingCount.toString()

                dateTvCard.text = it.createdDateFormatted
                launchStatusTv.text = it.status?.name

                setUpProbability(it.probability)

                //status
                setUpStatusType(it.status?.id , it)

                //startup timer
                setTimer(it.launchDate)




            }
        }
    }

    private fun calculateSuccessRate(t: Int, s: Int): Float {
        return (s.toFloat() / t.toFloat()) * 100
    }

    private fun setUpProbability(probability: Int?) {
        probability?.let {
            if( it < 0){
               binding.apply {
                   probabilityBar.progress = 0
                   percentageText.text = "0%"
               }
            }else{
                binding.apply {

                        probabilityBar.progress = it
                        percentageText.text = "$it%"


                }
            }
        }?:alternateProbability()
    }
    private fun alternateProbability(){
        binding.apply {
            probabilityBar.progress = 10
            percentageText.text = "10%"
        }
    }

    private fun setUpStatusType(statusId: Int?, launchList: LaunchList) {
        if(statusId == 1){
            binding.launchStatusTv.setTextColor(resources.getColor(R.color.starting_progress_color))
        }else if(statusId == 3){
            binding.apply {
                alternateDataTxt.isVisible = true
                alternateStatusTxt.isVisible = true
                alternateStatusTxt.text = launchList.status?.name
                alternateDataTxt.text = launchList.createdDateFormatted
                alternateStatusTxt.setTextColor(Color.GREEN)
                dateTvCard.isVisible = false
                countDownTv.isGone = true
                daysHrsTxt.isVisible = false
                launchStatusTv.isVisible = false
            }


        }else if (statusId == 4){

            binding.apply {
                alternateDataTxt.isVisible = true
                alternateStatusTxt.isVisible = true
                alternateStatusTxt.text = launchList.status?.name
                alternateDataTxt.text = launchList.createdDateFormatted
                alternateStatusTxt.setTextColor(Color.RED)
                countDownTv.isGone = true
                dateTvCard.isVisible = false
                daysHrsTxt.isVisible = false
                launchStatusTv.isVisible = false
            }
        }
    }

    private fun setUpToolbar(launchImage: String?, launchName: String?) {
        val toolbar = binding.launchDetailsToolbar as Toolbar
        toolbar.elevation = 0.0F
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar!!.title = launchName
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar!!.setHomeButtonEnabled(true)

        toolbar.setNavigationOnClickListener {
            binding.root.findNavController().popBackStack()
        }

        binding.toolbarImage.also {
            Glide.with(binding.root)
                .load(launchImage)
                .transition(DrawableTransitionOptions.withCrossFade())
                .centerCrop()
                .into(it)
        }

    }

    private fun setTimer(launchDate: Date) {
        val futureTimeMill = launchDate.time
        val c_date = Calendar.getInstance().timeInMillis
        var timeDiff = futureTimeMill - c_date

        countDownTimer = object : CountDownTimer(timeDiff , 1000) {
            override fun onTick(millscUntilFinish: Long) {
                binding.apply {

                    countDownTv.text = requireContext().getString(R.string.updated_timer,
                        TimeUnit.MILLISECONDS.toDays(millscUntilFinish) , TimeUnit.MILLISECONDS.toHours(millscUntilFinish) % 24  ,
                        TimeUnit.MILLISECONDS.toMinutes(millscUntilFinish) % 60  , TimeUnit.MILLISECONDS.toSeconds(millscUntilFinish) %60
                    )

                }
            }

            override fun onFinish() {
                binding.apply {
                    countDownTv.setTextColor(Color.GREEN)
                    countDownTv.text = requireContext().getText(R.string.vehicle_liftoff)
                    daysHrsTxt.isVisible = false
                }
            }
        }

        (countDownTimer as CountDownTimer).start()


    }

}