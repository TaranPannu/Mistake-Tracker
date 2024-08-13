package com.example.mistaketracker.Adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.mistaketracker.DetailFragment
import com.example.mistaketracker.MistakeListFragment

class VPAdapter(fa: FragmentActivity): FragmentStateAdapter(fa) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> {
                MistakeListFragment()
            }
            else -> DetailFragment()
        }
    }
}