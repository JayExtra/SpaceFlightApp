package com.dev.james.launchlibraryapi.features.ui

import android.os.Bundle
import android.view.View
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.dev.james.launchlibraryapi.R
import com.dev.james.launchlibraryapi.databinding.FragmentUpcomingLaunchesBinding
import com.dev.james.launchlibraryapi.features.adapters.FooterLoadStateAdapter
import com.dev.james.launchlibraryapi.features.adapters.LaunchListAdapter
import com.dev.james.launchlibraryapi.features.viewmodels.LaunchListViewModel
import com.dev.james.launchlibraryapi.models.LaunchList
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class UpcomingFragment : Fragment(R.layout.fragment_upcoming_launches) {
    private val viewModel : LaunchListViewModel by viewModels()
    private lateinit var binding : FragmentUpcomingLaunchesBinding
    private val launchListAdapter = LaunchListAdapter{ launchItem, message , image , missionName ->
        showSnackbar(message)
        navigate(launchItem , image , missionName)

    }

    private fun navigate(launch: LaunchList?, image: String?, missionName: String?) {
        launch?.let {
            //navigate to destination with arguments
            val action = HomeFragmentDirections.actionHomeFragmentToLaunchDetailsFragment2(
                launch,
                image!!,
                missionName!!
            )
            findNavController().navigate(action)
        }
    }

    private fun showSnackbar(s: String?) {
        s?.let { Snackbar.make(binding.root , it, Snackbar.LENGTH_SHORT).show() }
    }

    private var hasLoadedData = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentUpcomingLaunchesBinding.bind(view)

        setUpAdapter()
        setUpRecyclerView()
    }

    override fun onResume() {
        super.onResume()
        getLaunchList()
    }
    private fun setUpRecyclerView() {
        binding.apply {
            upcomingRv.apply {
                adapter = launchListAdapter.withLoadStateFooter(
                    footer = FooterLoadStateAdapter{retry()}
                )
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
            }
        }
    }

    private fun setUpAdapter() {

        launchListAdapter.addLoadStateListener { loadState ->
            if (loadState.refresh is LoadState.Loading && launchListAdapter.snapshot().isEmpty()
            ) {
                binding.progressBarUp.isVisible = true
                binding.imageView.isInvisible = true
                binding.errorUpcTxt.isInvisible = true

            } else {

                binding.progressBarUp.isVisible = false
                binding.imageView.isInvisible = true
                binding.errorUpcTxt.isInvisible = true

                //if there is error a textview will show the error encountered.

                val error = when {
                    loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                    loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                    loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error

                    else -> null
                }
                if (launchListAdapter.snapshot().isEmpty()) {
                    error?.let {
                        binding.errorUpcTxt.visibility = View.VISIBLE
                        binding.imageView.visibility = View.VISIBLE
                        binding.buttonUpRetry.visibility = View.VISIBLE
                        binding.buttonUpRetry.setOnClickListener {
                            launchListAdapter.retry()
                            binding.imageView.isInvisible = true
                            binding.errorUpcTxt.isInvisible = true
                            binding.buttonUpRetry.isInvisible = true
                        }
                    }

                }
            }

        }

    }


    private fun getLaunchList() {
        lifecycleScope.launch {
            viewModel.launchListUpcoming.collectLatest {
                launchListAdapter.submitData(it)
                hasLoadedData = true
            }
        }
    }

    private fun retry() {
        launchListAdapter.retry()
    }

}