package com.example.tbcacademy.presentation.recipe_details.contract

import com.example.tbcacademy.presentation.model.IngredientUi
import com.example.tbcacademy.presentation.model.RecipeDetailsUi

data class RecipeDetailState(
    val isLoading: Boolean = false,
    val recipe: RecipeDetailsUi? = null,
    val ingredients: List<IngredientUi> = emptyList(),
    val servings: Int = 1,
    val error: String? = null,
)

sealed interface RecipeDetailEvent {
    data class LoadData(val recipeId: Int) : RecipeDetailEvent
    data object IncreaseServings : RecipeDetailEvent
    data object DecreaseServings : RecipeDetailEvent
    data object ToggleBookmark : RecipeDetailEvent
}

sealed interface RecipeDetailSideEffect {
    data class ShowError(val message: String) : RecipeDetailSideEffect
}