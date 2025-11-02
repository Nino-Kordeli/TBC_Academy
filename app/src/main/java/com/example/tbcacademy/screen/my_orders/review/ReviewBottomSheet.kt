package com.example.tbcacademy.screen.my_orders.review

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tbcacademy.R
import com.example.tbcacademy.databinding.BottomSheetLayoutBinding
import com.example.tbcacademy.screen.my_orders.adapter.ProductAdapter
import com.example.tbcacademy.screen.my_orders.model.Product
import com.example.tbcacademy.screen.my_orders.model.Status
import com.example.tbcacademy.screen.orders.vm.ProductViewModel
import com.example.tbcacademy.utils.extensions.showSnackBar
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ReviewBottomSheet : BottomSheetDialogFragment() {

    private var _binding: BottomSheetLayoutBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProductViewModel by activityViewModels()
    private lateinit var product: Product
    private lateinit var singleItemAdapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        product = arguments?.getSerializable(ARG_PRODUCT, Product::class.java)
            ?: throw IllegalArgumentException("Product is required")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSingleItemRecyclerView()
        setupListeners()
    }

    private fun setupSingleItemRecyclerView() {
        singleItemAdapter = ProductAdapter(
            onReviewClicked = { clickedProduct ->
                binding.root.showSnackBar("Clicked ${clickedProduct.title}")
            },
            hideButtonInDrawer = true
        )

        singleItemAdapter.submitList(listOf(product))

        binding.rvSingleItem.apply {
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false
            )
            adapter = singleItemAdapter
            setHasFixedSize(true)
        }
    }

    private fun setupListeners() {
        binding.btnCancel.setOnClickListener { dismiss() }
        binding.btnSubmit.setOnClickListener { submitReview() }
    }

    private fun submitReview() {
        val rating = binding.starRating.rating
        val reviewText = binding.etReviewField.text.toString().trim()

        when {
            rating == 0f -> binding.root.showSnackBar(getString(R.string.please_provide_a_rating))
            reviewText.isEmpty() -> binding.root.showSnackBar(getString(R.string.please_write_a_review))
            else -> {
                viewModel.updateProductStatus(product.id, Status.COMPLETED)
                binding.root.showSnackBar(getString(R.string.review_submitted_successfully))
                dismiss()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "ReviewBottomSheet"
        private const val ARG_PRODUCT = "product"

        fun newInstance(product: Product): ReviewBottomSheet {
            return ReviewBottomSheet().apply {
                arguments = Bundle().apply { putSerializable(ARG_PRODUCT, product) }
            }
        }
    }
}