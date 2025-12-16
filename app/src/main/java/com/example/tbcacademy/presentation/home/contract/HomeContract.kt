package com.example.tbcacademy.presentation.home.contract

import com.example.tbcacademy.presentation.model.RecipeUi

data class HomeState(
    val isLoading: Boolean = false,
    val recipes: List<RecipeUi> = emptyList(),
    val error: String? = null,
)

sealed interface HomeEvent {
    data object LoadTrendingRecipes : HomeEvent
    data object Retry : HomeEvent
    data class OnRecipeClick(val id: Int) : HomeEvent
    data object NavigateToProfile : HomeEvent
    data class OnFavoriteClick(val id:Int): HomeEvent
}

sealed interface HomeSideEffect {
    data class ShowError(val message: String) : HomeSideEffect
    data class NavigateToRecipe(val id: Int) : HomeSideEffect
    data object NavigateToProfile : HomeSideEffect
}
