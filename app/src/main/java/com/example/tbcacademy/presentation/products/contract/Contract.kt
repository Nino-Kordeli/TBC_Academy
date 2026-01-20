package com.example.tbcacademy.presentation.products.contract

import com.example.tbcacademy.presentation.model.CategoryUi
import com.example.tbcacademy.presentation.model.ProductUi

data class ProductsUiState(
    val categories: List<CategoryUi> = emptyList(),
    val products: List<ProductUi> = emptyList(),
    val selectedCategory: String = "all",
    val isLoading: Boolean = false
)
sealed class ProductsEvent {
    data class CategorySelected(val category: String) : ProductsEvent()
}

sealed class ProductsSideEffect {
}
