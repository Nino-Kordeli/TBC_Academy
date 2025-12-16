package com.example.tbcacademy.presentation.fragment.home.fragment

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tbcacademy.common.BaseFragment
import com.example.tbcacademy.databinding.FragmentHomeBinding
import com.example.tbcacademy.presentation.fragment.home.adapter.TrendingRecipesAdapter
import com.example.tbcacademy.presentation.fragment.home.contract.HomeContract
import com.example.tbcacademy.presentation.fragment.home.vm.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val viewModel: HomeViewModel by viewModels()

    private val trendingAdapter by lazy {
        TrendingRecipesAdapter { recipeId ->
            viewModel.onEvent(HomeContract.HomeEvent.OnRecipeClick(recipeId))
        }
    }

    override fun bind() {
        setupRecyclerView()
        observeState()

        viewModel.onEvent(HomeContract.HomeEvent.LoadTrendingRecipes)
    }

    private fun setupRecyclerView() = with(binding.rvTrendingRecipes) {
        layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        adapter = trendingAdapter
    }

    private fun observeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collectLatest { state ->
                trendingAdapter.submitList(state.recipes)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.sideEffect.collectLatest { sideEffect ->
                when (sideEffect) {
                    is HomeContract.HomeSideEffect.NavigateToRecipe -> {
                    }
                    is HomeContract.HomeSideEffect.ShowError -> {
                    }
                }
            }
        }
    }
}