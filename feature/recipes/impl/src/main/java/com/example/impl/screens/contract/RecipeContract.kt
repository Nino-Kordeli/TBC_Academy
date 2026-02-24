package com.example.impl.screens.contract

import com.example.domain.model.recipe.RecipeCategory

data class RecipeState(
    val categories: List<RecipeCategory> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

sealed interface RecipeEvent

sealed interface RecipeSideEffect