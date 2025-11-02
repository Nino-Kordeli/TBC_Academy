package com.example.tbcacademy.screen.completed_orders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tbcacademy.common.BaseFragment
import com.example.tbcacademy.databinding.FragmentCompletedOrdersBinding
import com.example.tbcacademy.screen.my_orders.adapter.ProductAdapter
import com.example.tbcacademy.screen.my_orders.model.Status
import com.example.tbcacademy.screen.orders.vm.ProductViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class CompletedOrdersFragment : BaseFragment<FragmentCompletedOrdersBinding>() {

    private val viewModel: ProductViewModel by activityViewModels()
    private lateinit var adapter: ProductAdapter

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCompletedOrdersBinding {
        return FragmentCompletedOrdersBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: android.view.View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeProducts()
    }

    private fun setupRecyclerView() {
        adapter = ProductAdapter(hideButtonInDrawer = true)
        binding.rvCompletedOrders.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@CompletedOrdersFragment.adapter
        }
    }

    private fun observeProducts() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.products.collectLatest { products ->
                adapter.submitList(products.filter { it.status == Status.COMPLETED })
            }
        }
    }
}
