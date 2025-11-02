package com.example.tbcacademy.screen.view_pager.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.tbcacademy.screen.active.ActiveOrdersFragment
import com.example.tbcacademy.screen.completed.CompletedOrdersFragment

class OrdersPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount() = 2
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> ActiveOrdersFragment()
            1 -> CompletedOrdersFragment()
            else -> throw IllegalArgumentException("Invalid position $position")
        }
    }
}