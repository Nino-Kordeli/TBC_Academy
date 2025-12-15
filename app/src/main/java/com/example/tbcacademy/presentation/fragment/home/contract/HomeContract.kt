package com.example.tbcacademy.presentation.fragment.home.contract

import com.example.tbcacademy.presentation.model.RecipeUi

object HomeContract {

    data class HomeState(
        val isLoading: Boolean = false,
        val recipes: List<RecipeUi> = emptyList(),
        val error: String? = null,
    )

    sealed interface HomeEvent {
        data object LoadTrendingRecipes : HomeEvent
        data object Retry : HomeEvent
        data class OnRecipeClick(val id: Int) : HomeEvent
    }

    sealed interface HomeSideEffect {
        data class ShowError(val message: String) : HomeSideEffect
        data class NavigateToRecipe(val id: Int) : HomeSideEffect
    }
}
