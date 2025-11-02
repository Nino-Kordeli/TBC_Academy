package com.example.tbcacademy.screen.my_orders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.tbcacademy.common.BaseFragment
import com.example.tbcacademy.databinding.FragmentMyOrdersBinding
import com.example.tbcacademy.screen.active_orders.ActiveOrdersFragment
import com.example.tbcacademy.screen.completed_orders.CompletedOrdersFragment
import com.example.tbcacademy.screen.view_pager.adapter.OrdersPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.example.tbcacademy.screen.my_orders.model.Status

class MyOrdersFragment : BaseFragment<FragmentMyOrdersBinding>() {
    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMyOrdersBinding {
        return FragmentMyOrdersBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: android.view.View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragments = listOf(
            ActiveOrdersFragment.newInstance(Status.ACTIVE),
            CompletedOrdersFragment()
        )

        val adapter = OrdersPagerAdapter(this, fragments)
        binding.viewPager.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = if (position == 0) "Active" else "Completed"
        }.attach()
    }
}
