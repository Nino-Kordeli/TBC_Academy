package com.example.tbcacademy.screen.my_orders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tbcacademy.screen.adapter.DeliveryAdapter
import com.example.tbcacademy.screen.dialog.DeliveryDetailsDialog
import com.example.tbcacademy.screen.model.DeliveryItem
import com.example.tbcacademy.screen.vm.OrdersViewModel
import com.example.tbcacademy.R
import com.example.tbcacademy.screen.model.Status
import com.example.tbcacademy.common.BaseFragment
import com.example.tbcacademy.databinding.FragmentMyOrdersBinding
import kotlinx.coroutines.launch

class MyOrdersFragment : BaseFragment<FragmentMyOrdersBinding>() {

    private val viewModel: OrdersViewModel by viewModels()

    private val adapter by lazy {
        DeliveryAdapter { item ->
            showDetailsDialog(item)
        }
    }

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMyOrdersBinding = FragmentMyOrdersBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupFilterButtons()
        observeFilteredItems()
        selectFilter(Status.PENDING)
    }

    private fun setupRecyclerView() {
        binding.recyclerViewItems.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@MyOrdersFragment.adapter
        }
    }

    private fun setupFilterButtons() {
        binding.apply {
            btnPending.setOnClickListener { selectFilter(Status.PENDING) }
            btnDelivered.setOnClickListener { selectFilter(Status.DELIVERED) }
            btnCanceled.setOnClickListener { selectFilter(Status.CANCELED) }
        }
    }

    private fun observeFilteredItems() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.filteredItems.collect { items ->
                adapter.submitList(items)
            }
        }
    }

    private fun showDetailsDialog(item: DeliveryItem) {
        DeliveryDetailsDialog(item) { updatedItem, newStatus ->
            viewModel.updateItemStatus(updatedItem, newStatus)
        }.show(parentFragmentManager, "delivery_details")
    }

    private fun selectFilter(status: Status) {
        viewModel.setStatusFilter(status)
        updateButtonDesign(status)
    }

    private fun updateButtonDesign(selected: Status) {
        val context = requireContext()
        val defaultBg = ContextCompat.getDrawable(context, R.drawable.button_default_design)
        val toggledBg = ContextCompat.getDrawable(context, R.drawable.button_toggled_design)
        val defaultTextColor = ContextCompat.getColor(context, R.color.black)
        val toggledTextColor = ContextCompat.getColor(context, R.color.white)

        binding.apply {
            btnPending.apply {
                background = if (selected == Status.PENDING) toggledBg else defaultBg
                setTextColor(if (selected == Status.PENDING) toggledTextColor else defaultTextColor)
            }

            btnDelivered.apply {
                background = if (selected == Status.DELIVERED) toggledBg else defaultBg
                setTextColor(if (selected == Status.DELIVERED) toggledTextColor else defaultTextColor)
            }

            btnCanceled.apply {
                background = if (selected == Status.CANCELED) toggledBg else defaultBg
                setTextColor(if (selected == Status.CANCELED) toggledTextColor else defaultTextColor)
            }
        }
    }
}