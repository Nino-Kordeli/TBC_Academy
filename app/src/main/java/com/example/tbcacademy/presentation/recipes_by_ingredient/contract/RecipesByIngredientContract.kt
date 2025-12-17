package com.example.tbcacademy.presentation.recipes_by_ingredient.contract

import com.example.tbcacademy.presentation.model.RecipeUi

data class RecipesByIngredientState(
    val recipes: List<RecipeUi> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

sealed interface RecipesByIngredientEvent {
    data class LoadRecipes(val ingredientId: Int) : RecipesByIngredientEvent
}

sealed interface RecipesByIngredientSideEffect {
    data class ShowError(val message: String) : RecipesByIngredientSideEffect
}
