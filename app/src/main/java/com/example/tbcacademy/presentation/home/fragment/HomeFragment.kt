package com.example.tbcacademy.presentation.home.fragment

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tbcacademy.common.BaseFragment
import com.example.tbcacademy.databinding.FragmentHomeBinding
import com.example.tbcacademy.presentation.home.adapter.TrendingRecipesAdapter
import com.example.tbcacademy.presentation.home.contract.HomeEvent
import com.example.tbcacademy.presentation.home.contract.HomeSideEffect
import com.example.tbcacademy.presentation.home.vm.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment :
    BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val viewModel: HomeViewModel by viewModels()

    private val trendingAdapter by lazy {
        TrendingRecipesAdapter { recipeId ->
            viewModel.onEvent(HomeEvent.OnRecipeClick(recipeId))
        }
    }

    override fun bind() {
        setupRecyclerView()
        observeState()
        setListeners()
        viewModel.onEvent(HomeEvent.LoadTrendingRecipes)
    }

    private fun setListeners() {
        binding.ivUserIcon.setOnClickListener {
            viewModel.onEvent(HomeEvent.NavigateToProfile)
        }
    }


    private fun setupRecyclerView() = with(binding.rvTrendingRecipes) {
        layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
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

                    is HomeSideEffect.NavigateToRecipe -> {
                        findNavController().navigate(
                            HomeFragmentDirections.actionHomeFragmentToRecipeDetailsFragment(
                                recipeId = sideEffect.id
                            )
                        )
                    }

                    is HomeSideEffect.ShowError -> {
                        // show error UI if needed
                    }

                    HomeSideEffect.NavigateToProfile -> {
                        findNavController().navigate(
                            HomeFragmentDirections.actionHomeFragmentToUserDetailsFragment()
                        )
                    }
                }
            }
        }
    }
}
