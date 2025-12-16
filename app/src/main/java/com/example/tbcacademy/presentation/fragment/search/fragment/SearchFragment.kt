package com.example.tbcacademy.presentation.fragment.search.fragment

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tbcacademy.common.BaseFragment
import com.example.tbcacademy.databinding.FragmentSearchBinding
import com.example.tbcacademy.presentation.fragment.search.adapter.IngredientAdapter
import com.example.tbcacademy.presentation.fragment.search.vm.SearchViewModel
import kotlinx.coroutines.flow.collectLatest
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.tbcacademy.domain.model.FoodCategory
import com.example.tbcacademy.presentation.fragment.search.adapter.MealCategoryAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment :
    BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::inflate) {

    private val viewModel: SearchViewModel by viewModels()

    private val ingredientAdapter by lazy {
        IngredientAdapter { ingredient ->
        }
    }

    private val mealCategoryAdapter by lazy {
        MealCategoryAdapter(
            listOf(
                FoodCategory.BREAKFAST,
                FoodCategory.LUNCH,
                FoodCategory.DINNER,
                FoodCategory.SNACKS,
                FoodCategory.DESSERT,
                FoodCategory.DRINKS
            )
        )
    }

    override fun bind() {
        setupIngredientRecycler()
        setupMealCategoryRecycler()
        observeState()

        viewModel.onEvent(
            com.example.tbcacademy.presentation.fragment.search.contract.SearchContract
                .SearchEvent.LoadIngredients
        )
    }

    private fun setupIngredientRecycler() = with(binding.rvIngredients) {
        layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        adapter = ingredientAdapter
    }

    private fun setupMealCategoryRecycler() = with(binding.rvMealCategories) {
        layoutManager = GridLayoutManager(requireContext(), 2)
        adapter = mealCategoryAdapter
    }

    private fun observeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collectLatest { state ->
                ingredientAdapter.submitList(state.ingredients)
            }
        }
    }
}
