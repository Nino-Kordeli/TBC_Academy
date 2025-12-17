package com.example.tbcacademy.presentation.search.fragment

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.GridLayoutManager
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.tbcacademy.common.BaseFragment
import com.example.tbcacademy.databinding.FragmentSearchBinding
import com.example.tbcacademy.presentation.search.adapter.IngredientAdapter
import com.example.tbcacademy.presentation.search.adapter.MealCategoryAdapter
import com.example.tbcacademy.presentation.search.vm.SearchViewModel
import com.example.tbcacademy.domain.model.FoodCategory
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment :
    BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::inflate) {

    private val viewModel: SearchViewModel by viewModels()

    private val ingredientAdapter by lazy {
        IngredientAdapter { ingredient ->
            findNavController().navigate(
                SearchFragmentDirections
                    .actionSearchFragmentToRecipesByIngredientFragment(ingredient.id)
            )
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
            com.example.tbcacademy.presentation.search.contract.SearchContract
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
