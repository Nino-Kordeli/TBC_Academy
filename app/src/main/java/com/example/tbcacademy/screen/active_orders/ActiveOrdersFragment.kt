package com.example.tbcacademy.screen.active_orders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tbcacademy.common.BaseFragment
import com.example.tbcacademy.databinding.FragmentActiveBinding
import com.example.tbcacademy.screen.my_orders.adapter.ProductAdapter
import com.example.tbcacademy.screen.my_orders.model.Status
import com.example.tbcacademy.screen.my_orders.review.ReviewBottomSheet
import com.example.tbcacademy.screen.orders.vm.ProductViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ActiveOrdersFragment : BaseFragment<FragmentActiveBinding>() {

    private val viewModel: ProductViewModel by activityViewModels()
    private lateinit var adapter: ProductAdapter
    private var orderStatus: Status = Status.ACTIVE

    override fun inflateBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentActiveBinding.inflate(inflater, container, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        orderStatus = arguments?.getSerializable(ARG_STATUS, Status::class.java) ?: Status.ACTIVE
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeProducts()
    }

    private fun setupRecyclerView() {
        adapter = ProductAdapter(
            onReviewClicked = { product ->
                if (product.status == Status.ACTIVE) {
                    ReviewBottomSheet.newInstance(product)
                        .show(parentFragmentManager, ReviewBottomSheet.TAG)
                }
            },
            hideButtonInDrawer = false
        )
        binding.rvActiveOrders.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@ActiveOrdersFragment.adapter
            setHasFixedSize(true)
        }
    }

    private fun observeProducts() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.products.collectLatest { products ->
                    adapter.submitList(products.filter { it.status == orderStatus })
                }
            }
        }
    }

    companion object {
        private const val ARG_STATUS = "status"
        fun newInstance(status: Status) = ActiveOrdersFragment().apply {
            arguments = Bundle().apply { putSerializable(ARG_STATUS, status) }
        }
    }
}
