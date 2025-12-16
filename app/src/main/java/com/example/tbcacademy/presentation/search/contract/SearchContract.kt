package com.example.tbcacademy.presentation.search.contract

import com.example.tbcacademy.presentation.model.IngredientUi

object SearchContract {

    data class SearchState(
        val ingredients: List<IngredientUi> = emptyList(),
        val isLoading: Boolean = false,
        val error: String? = null
    )

    sealed interface SearchEvent {
        object LoadIngredients : SearchEvent
    }

    sealed interface SearchSideEffect {
        data class ShowError(val message: String) : SearchSideEffect
    }
}
