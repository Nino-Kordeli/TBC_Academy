package com.example.tbcacademy.presentation.recipe_details.fragment

import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.tbcacademy.common.BaseFragment
import com.example.tbcacademy.databinding.FragmentRecipeDetailsBinding
import com.example.tbcacademy.presentation.recipe_details.adapter.RecipeIngredientsAdapter
import com.example.tbcacademy.presentation.recipe_details.contract.RecipeDetailEvent
import com.example.tbcacademy.presentation.recipe_details.contract.RecipeDetailSideEffect
import com.example.tbcacademy.presentation.recipe_details.vm.RecipeDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RecipeDetailsFragment : BaseFragment<FragmentRecipeDetailsBinding>(
    FragmentRecipeDetailsBinding::inflate
) {

    private val viewModel: RecipeDetailViewModel by viewModels()
    private val args: RecipeDetailsFragmentArgs by navArgs()

    private val ingredientsAdapter by lazy { RecipeIngredientsAdapter() }

    override fun bind() {
        setupRecyclerView()
        observeState()
        viewModel.onEvent(RecipeDetailEvent.LoadData(args.recipeId))
    }

    private fun setupRecyclerView() = binding.rvIngredients.apply {
        layoutManager = LinearLayoutManager(requireContext())
        adapter = ingredientsAdapter
        isNestedScrollingEnabled = false
    }


    private fun observeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collectLatest { state ->

                state.recipe?.let { recipe ->
                    binding.tvRecipeName.text = recipe.name
                    binding.tvDescription.text = recipe.description

                    Glide.with(requireContext())
                        .load(recipe.imageUrl)
                        .into(binding.ivRecipeImage)

                    val directionsText = recipe.directions.mapIndexed { index, step ->
                        "${index + 1}. $step"
                    }.joinToString("\n\n")

                    binding.tvDirections.text = directionsText
                }

                ingredientsAdapter.submitList(state.ingredients)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.sideEffect.collectLatest { sideEffect ->
                when (sideEffect) {
                    is RecipeDetailSideEffect.ShowError ->
                        Toast.makeText(requireContext(), sideEffect.message, Toast.LENGTH_SHORT)
                            .show()
                }
            }
        }
    }
}
