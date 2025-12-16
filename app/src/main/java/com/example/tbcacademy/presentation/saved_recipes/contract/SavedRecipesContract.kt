package com.example.tbcacademy.presentation.saved_recipes.contract

import com.example.tbcacademy.presentation.model.RecipeUi

data class SavedRecipesState(
    val recipes: List<RecipeUi> = emptyList(),
    val favouriteCount: Int = 0,
    val isLoading: Boolean = false,
    val error: String? = null
)

sealed interface SavedRecipesEvent {
    data class OnRecipeClick(val id: Int) : SavedRecipesEvent
    data class OnFavoriteClick(val id: Int) : SavedRecipesEvent
}

sealed interface SavedRecipesSideEffect {
    data class NavigateToRecipe(val id: Int) : SavedRecipesSideEffect
}