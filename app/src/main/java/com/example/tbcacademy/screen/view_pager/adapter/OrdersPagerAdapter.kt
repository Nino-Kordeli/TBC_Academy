package com.example.tbcacademy.screen.view_pager.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.tbcacademy.screen.active_orders.ActiveOrdersFragment
import com.example.tbcacademy.screen.my_orders.model.Status

class OrdersPagerAdapter(fragment: Fragment, fragments: List<Fragment>) : FragmentStateAdapter(fragment) {

    override fun getItemCount() = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> ActiveOrdersFragment.newInstance(Status.ACTIVE)
            1 -> ActiveOrdersFragment.newInstance(Status.COMPLETED)
            else -> throw IllegalArgumentException("Invalid position $position")
        }
    }
}