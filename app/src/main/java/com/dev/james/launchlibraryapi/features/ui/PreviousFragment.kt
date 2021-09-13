package com.dev.james.launchlibraryapi.features.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.dev.james.launchlibraryapi.R
import com.dev.james.launchlibraryapi.databinding.FragmentPreviousLaunchesBinding
import com.dev.james.launchlibraryapi.features.adapters.FooterLoadStateAdapter
import com.dev.james.launchlibraryapi.features.adapters.LaunchListAdapter
import com.dev.james.launchlibraryapi.features.viewmodels.LaunchListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class PreviousFragment : Fragment(R.layout.fragment_previous_launches) {
    private val viewModel : LaunchListViewModel by activityViewModels()
    private var _binding : FragmentPreviousLaunchesBinding? = null
    private val binding get() = _binding
    private val launchListAdapter = LaunchListAdapter()
    private val TAG ="previousFragment"
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPreviousLaunchesBinding.inflate(layoutInflater , container , false)
        getLaunchList()
        setUpAdapter()
        setUpRecyclerView()

        return binding?.root

    }

    private fun setUpRecyclerView() {
        binding?.apply {
            previousRv.apply {
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
                binding?.progressPrevious?.isVisible = true

            } else {

                binding?.progressPrevious?.isVisible = false

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
                        binding?.errorTxt?.visibility = View.VISIBLE
                        binding?.errorTxt?.text = it.toString()
                        binding?.netImageError?.visibility = View.VISIBLE
                        binding?.prevRetryButton?.visibility = View.VISIBLE
                        binding?.prevRetryButton?.setOnClickListener {
                            launchListAdapter.retry()
                        }
                    }

                }
            }

        }

    }

    private fun getLaunchList() {
        lifecycleScope.launch {
            viewModel.getLaunches(0).collectLatest {
                launchListAdapter.submitData(it)
            }
        }
    }

    private fun retry() {
        launchListAdapter.retry()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}