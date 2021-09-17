package com.dev.james.launchlibraryapi.features.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.dev.james.launchlibraryapi.R
import com.dev.james.launchlibraryapi.databinding.FragmentPreviousLaunchesBinding
import com.dev.james.launchlibraryapi.features.adapters.FooterLoadStateAdapter
import com.dev.james.launchlibraryapi.features.adapters.LaunchListAdapter
import com.dev.james.launchlibraryapi.features.viewmodels.LaunchListViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class PreviousFragment : Fragment(R.layout.fragment_previous_launches) {
    private val viewModel : LaunchListViewModel by viewModels()
    private lateinit var binding : FragmentPreviousLaunchesBinding
    private val launchListAdapter = LaunchListAdapter { status , b  ->
        showSnackBar(status)
        navigate(b)
    }

    private fun navigate(b: Boolean) {
        if(b){
            binding.root.findNavController().navigate(
                R.id.action_homeFragment_to_launchDetailsFragment2
            )
        }
    }

    private val TAG ="PreviousFragment"
    private var hasLoadedData : Boolean = false
    private var submitEmptyList : Boolean = true
    private var job : Job? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPreviousLaunchesBinding.bind(view)
        setUpAdapter()
    }


    private fun setUpAdapter() {

        binding.apply {
            previousRv.apply {
                adapter = launchListAdapter.withLoadStateFooter(
                    footer = FooterLoadStateAdapter{retry()}
                )
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
            }
        }

        if(!hasLoadedData) getLaunchList() ; hasLoadedData = true


        launchListAdapter.addLoadStateListener { loadState ->
            if (loadState.refresh is LoadState.Loading && launchListAdapter.snapshot().isEmpty()
            ) {
                binding.progressPrevious.isVisible = true
                binding.netImageError.isInvisible = true
                binding.errorTxt.isInvisible = true

            } else {

                binding.progressPrevious.isVisible = false
                binding.netImageError.isInvisible = true
                binding.errorTxt.isInvisible = true

                //if there is error a textview will show the error encountered.

                val error = when {
                    loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                    loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                    loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error

                    else -> null
                }
                if (launchListAdapter.snapshot().isEmpty()) {
                    error?.let {
                        Log.d(TAG, "setUpAdapter: ${it.toString()}")
                        binding.errorTxt.visibility = View.VISIBLE
                        binding.errorTxt.text = it.toString()
                        binding.netImageError.visibility = View.VISIBLE
                        binding.prevRetryButton.visibility = View.VISIBLE
                        binding.prevRetryButton.setOnClickListener {
                            launchListAdapter.retry()
                            binding.netImageError.isInvisible = true
                            binding.errorTxt.isInvisible = true
                            binding.prevRetryButton.isInvisible = true

                        }
                    }

                }
            }

        }

    }

    private fun getLaunchList() {
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.launchListPrevious.collectLatest {
                    launchListAdapter.submitData(it)
                    hasLoadedData = true
                }
            }
    }

    private fun retry() {
        launchListAdapter.retry()
    }

    private fun showSnackBar(message : String?){
        message?.let { Snackbar.make(binding.root , it, Snackbar.LENGTH_SHORT).show() }
    }
}