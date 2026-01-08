package com.example.tbcacademy.presentation.search.fragment

import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tbcacademy.presentation.common.BaseFragment
import com.example.tbcacademy.databinding.FragmentSearchBinding
import com.example.tbcacademy.presentation.search.adapter.CategoryAdapter
import com.example.tbcacademy.presentation.search.contract.CategoryEvent
import com.example.tbcacademy.presentation.search.contract.CategorySideEffect
import com.example.tbcacademy.presentation.search.vm.CategoryViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::inflate) {

    private val viewModel: CategoryViewModel by viewModels()
    private val adapter by lazy { CategoryAdapter() }

    override fun bind() {
        binding.rvItems.layoutManager = LinearLayoutManager(requireContext())
        binding.rvItems.adapter = adapter

        binding.etSearchBar.addTextChangedListener {
            viewModel.onEvent(CategoryEvent.SearchQueryChanged(it.toString()))
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.state.collect { state ->
                        adapter.submitList(state.categories)
                    }
                }
                launch {
                    viewModel.sideEffect.collect { effect ->
                        when (effect) {
                            is CategorySideEffect.ShowError -> {
                                Toast.makeText(requireContext(), effect.message, Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            }
        }
    }
}