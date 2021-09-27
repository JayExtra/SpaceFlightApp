package com.dev.james.launchlibraryapi.features.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.dev.james.launchlibraryapi.R
import com.dev.james.launchlibraryapi.databinding.FragmentHomeBinding
import com.dev.james.launchlibraryapi.features.adapters.ViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var binding : FragmentHomeBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)

        setUpToolbar()


        val fragmentList = arrayListOf(
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
        val toolbar = binding.homeToolbar
        toolbar.elevation = 0.0F
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar!!.title = "Launch App"

    }

}