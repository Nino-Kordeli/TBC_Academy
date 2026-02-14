package com.example.tbcacademy.presentation.screens.search.contract

import com.example.tbcacademy.presentation.model.FoodModel

data class SearchState(
    val query: String = "",
    val foods: List<FoodModel> = emptyList(),
    val isLoading: Boolean = false
)

sealed interface SearchEvent {
    data class OnQueryChanged(val query: String) : SearchEvent
    object SearchClicked : SearchEvent
}

sealed interface SearchSideEffect {
    data class ShowError(val message: String) : SearchSideEffect
}