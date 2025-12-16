package com.example.tbcacademy.presentation.home.contract

import com.example.tbcacademy.presentation.model.RecipeUi

data class HomeState(
    val recipes: List<RecipeUi> = emptyList(),
    val favouriteCount: Int = 0,
    val isLoading: Boolean = false,
    val error: String? = null,
)

sealed interface HomeEvent {
    data object LoadTrendingRecipes : HomeEvent
    data object Retry : HomeEvent
    data class OnRecipeClick(val id: Int) : HomeEvent
    data object NavigateToProfile : HomeEvent
    data class OnFavoriteClick(val id: Int) : HomeEvent
    data object OnFavouriteIconClick : HomeEvent
}

sealed interface HomeSideEffect {
    data class ShowError(val message: String) : HomeSideEffect
    data object NavigateToFavourites : HomeSideEffect
    data class NavigateToRecipe(val id: Int) : HomeSideEffect
    data object NavigateToProfile : HomeSideEffect
}
