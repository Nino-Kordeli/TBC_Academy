package com.example.tbcacademy.presentation.search.contract

import com.example.tbcacademy.domain.model.Category

data class CategoryState(
    val isLoading: Boolean = false,
    val categories: List<Category> = emptyList(),
    val error: String? = null,
)

sealed interface CategoryEvent {
    data class SearchQueryChanged(val query: String) : CategoryEvent
}

sealed interface CategorySideEffect {
    data class ShowError(val message: String) : CategorySideEffect
}