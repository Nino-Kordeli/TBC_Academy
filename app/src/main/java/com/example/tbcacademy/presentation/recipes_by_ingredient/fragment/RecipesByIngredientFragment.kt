package com.example.tbcacademy.presentation.recipes_by_ingredient.fragment

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.example.tbcacademy.common.BaseFragment
import com.example.tbcacademy.databinding.FragmentRecipesByIngredientBinding
import com.example.tbcacademy.presentation.recipes_by_ingredient.adapter.RecipesByIngredientAdapter
import com.example.tbcacademy.presentation.recipes_by_ingredient.contract.RecipesByIngredientEvent
import com.example.tbcacademy.presentation.recipes_by_ingredient.vm.RecipesByIngredientViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RecipesByIngredientFragment :
    BaseFragment<FragmentRecipesByIngredientBinding>(FragmentRecipesByIngredientBinding::inflate) {

    private val viewModel: RecipesByIngredientViewModel by viewModels()
    private val args: RecipesByIngredientFragmentArgs by navArgs()

    private val adapter by lazy {
        RecipesByIngredientAdapter { recipeId ->
            findNavController().navigate(
                RecipesByIngredientFragmentDirections
                    .actionRecipesByIngredientFragmentToRecipeDetailsFragment(recipeId)
            )
        }
    }

    override fun bind() {
        setupRecycler()
        observeState()

        viewModel.onEvent(
            RecipesByIngredientEvent.LoadRecipes(args.ingredientId)
        )
    }

    private fun setupRecycler() = with(binding.rvRecipesByIngredients) {
        layoutManager = GridLayoutManager(requireContext(), 1)
        adapter = this@RecipesByIngredientFragment.adapter
    }

    private fun observeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collectLatest { state ->
                adapter.submitList(state.recipes)
            }
        }
    }
}
