package com.example.tbcacademy.presentation.recipe_details.vm

import android.util.Log.d
import androidx.lifecycle.viewModelScope
import com.example.tbcacademy.common.BaseViewModel
import com.example.tbcacademy.data.common.Resource
import com.example.tbcacademy.domain.usecase.ingredient.GetIngredientsUseCase
import com.example.tbcacademy.domain.usecase.recipe.GetRecipeDetailsUseCase
import com.example.tbcacademy.presentation.recipe_details.mapper.toUi
import com.example.tbcacademy.presentation.recipe_details.contract.RecipeDetailEvent
import com.example.tbcacademy.presentation.recipe_details.contract.RecipeDetailEvent.DecreaseServings
import com.example.tbcacademy.presentation.recipe_details.contract.RecipeDetailEvent.IncreaseServings
import com.example.tbcacademy.presentation.recipe_details.contract.RecipeDetailEvent.LoadData
import com.example.tbcacademy.presentation.recipe_details.contract.RecipeDetailEvent.ToggleBookmark
import com.example.tbcacademy.presentation.recipe_details.contract.RecipeDetailSideEffect
import com.example.tbcacademy.presentation.recipe_details.contract.RecipeDetailState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeDetailViewModel @Inject constructor(
    private val getRecipeDetailsUseCase: GetRecipeDetailsUseCase,
    private val getIngredientsUseCase: GetIngredientsUseCase,
) : BaseViewModel<
        RecipeDetailState,
        RecipeDetailEvent,
        RecipeDetailSideEffect>(
    RecipeDetailState()
) {

    override fun onEvent(event: RecipeDetailEvent) {
        when (event) {
            is LoadData -> loadRecipe(event.recipeId)
            IncreaseServings -> changeServings(1)
            DecreaseServings -> changeServings(-1)
            ToggleBookmark -> toggleBookmark()
        }
    }

    private fun loadRecipe(recipeId: Int) = viewModelScope.launch {
        getRecipeDetailsUseCase.invoke().collect {
            when (it) {
                is Resource.Error -> updateState { currentState ->
                    currentState.copy(error = it.errorMessage)
                }

                is Resource.Loading -> {
                    updateState { currentState -> currentState.copy(isLoading = it.loading) }
                }

                is Resource.Success -> {
                    val recipe = it.data.first { item -> item.id == recipeId }.toUi()
                    updateState { currentState -> currentState.copy(recipe = recipe) }

                    getIngredientsForRecipe()
                }
            }
        }
    }

    private fun getIngredientsForRecipe() = viewModelScope.launch {
        getIngredientsUseCase.invoke().collect {
            when (it) {
                is Resource.Error -> updateState { currentState ->
                    currentState.copy(error = it.errorMessage)
                }

                is Resource.Loading -> {
                    updateState { currentState -> currentState.copy(isLoading = it.loading) }
                }

                is Resource.Success -> {
                    val ingredientIds = state.value.recipe?.ingredientIds.orEmpty()

                    val filteredIngredients = it.data
                        .filter { ingredient -> ingredient.id in ingredientIds }
                        .toUi()

                    updateState { currentState ->
                        currentState.copy(ingredients = filteredIngredients)
                    }
                }
            }
        }
    }

    private fun changeServings(amount: Int) {
        val current = state.value.servings
        val newServings = (current + amount).coerceAtLeast(1)
        updateState { it.copy(servings = newServings) }
    }

    private fun toggleBookmark() {

    }

    private fun getQuantityForIngredient(index: Int) = when (index % 5) {
        0 -> "200g"
        1 -> "2 pieces"
        2 -> "1 cup"
        3 -> "3 tbsp"
        4 -> "1 tsp"
        else -> "To taste"
    }
}