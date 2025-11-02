package com.example.tbcacademy.screen.my_orders

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.tbcacademy.R
import com.example.tbcacademy.screen.adapter.OrdersPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator

class MyOrdersFragment : Fragment(R.layout.fragment_my_orders) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewPager = view.findViewById<ViewPager2>(R.id.viewPager)
        viewPager.adapter = OrdersPagerAdapter(this)
        viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        val tabLayout =
            view.findViewById<com.google.android.material.tabs.TabLayout>(R.id.tabLayout)
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> getString(R.string.active)
                1 -> getString(R.string.completed)
                else -> null
            }.toString()
        }.attach()
    }
}