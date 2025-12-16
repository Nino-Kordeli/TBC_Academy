package com.example.tbcacademy.presentation.saved_recipes.fragment

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.tbcacademy.common.BaseFragment
import com.example.tbcacademy.databinding.FragmentSavedRecipesBinding
import com.example.tbcacademy.presentation.saved_recipes.adapter.SavedRecipesAdapter
import com.example.tbcacademy.presentation.saved_recipes.contract.SavedRecipesEvent
import com.example.tbcacademy.presentation.saved_recipes.contract.SavedRecipesSideEffect
import com.example.tbcacademy.presentation.saved_recipes.vm.SavedRecipesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SavedRecipesFragment :
    BaseFragment<FragmentSavedRecipesBinding>(FragmentSavedRecipesBinding::inflate) {

    private val viewModel: SavedRecipesViewModel by viewModels()

    private val savedAdapter by lazy {
        SavedRecipesAdapter(
            onRecipeClick = { viewModel.onEvent(SavedRecipesEvent.OnRecipeClick(it)) },
            onFavoriteClick = { viewModel.onEvent(SavedRecipesEvent.OnFavoriteClick(it)) }
        )
    }

    override fun bind() {
        setupRecyclerView()
        observeState()

    }

    private fun setupRecyclerView() = with(binding.rvSavedRecipes) {
        layoutManager = GridLayoutManager(requireContext(), 1)
        adapter = savedAdapter
    }

    private fun observeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collectLatest { state ->
                savedAdapter.submitList(state.recipes)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.sideEffect.collectLatest { sideEffect ->
                when (sideEffect) {
                    is SavedRecipesSideEffect.NavigateToRecipe -> {
                        findNavController().navigate(
                            // replace with your nav action
                            SavedRecipesFragmentDirections.actionSavedRecipesFragmentToRecipeDetailsFragment(sideEffect.id)
                        )
                    }
                }
            }
        }
    }
}
