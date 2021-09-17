package com.dev.james.launchlibraryapi.features.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.dev.james.launchlibraryapi.R
import com.dev.james.launchlibraryapi.databinding.FragmentHomeBinding
import com.dev.james.launchlibraryapi.features.adapters.ViewPagerAdapter
import com.dev.james.launchlibraryapi.features.viewmodels.LaunchListViewModel
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var binding : FragmentHomeBinding
    private val viewModel : LaunchListViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)

        setUpToolbar()


        val fragmentList = arrayListOf<Fragment>(
            UpcomingFragment(),
            PreviousFragment()
        )
        val adapter = ViewPagerAdapter(
            fragmentList,
            childFragmentManager,
            lifecycle
        )
        binding.apply {
            homeViewPager.adapter = adapter
            TabLayoutMediator(homeTabLayout , homeViewPager ) { tab , position ->
                when(position){
                    0 -> {
                        tab.text = "upcoming"
                    }
                    1-> {
                        tab.text = "previous"
                    }
                }
            }.attach()
        }
    }




    private fun setUpToolbar() {
        val toolbar = binding.homeToolbar as Toolbar
        toolbar.elevation = 0.0F
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar!!.title = "Launch App"

    }

}