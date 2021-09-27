package com.dev.james.launchlibraryapi.features.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import java.util.ArrayList

class ViewPagerAdapter(
    list : ArrayList<Fragment> ,
    fragmentManager: FragmentManager,
    lifecycle : Lifecycle ) :
    FragmentStateAdapter(fragmentManager , lifecycle) {

    private val fragmentList = list
    override fun getItemCount(): Int {
        return fragmentList.size
    }

    override fun createFragment(position: Int): Fragment {
       return fragmentList[position]
    }


}