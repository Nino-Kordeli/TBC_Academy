package com.example.tbcacademy.presentation.products.vm

import androidx.lifecycle.viewModelScope
import com.example.tbcacademy.domain.usecase.GetCategoriesUseCase
import com.example.tbcacademy.domain.usecase.GetProductsUseCase
import com.example.tbcacademy.presentation.common.BaseViewModel
import com.example.tbcacademy.presentation.products.contract.ProductsEvent
import com.example.tbcacademy.presentation.products.contract.ProductsSideEffect
import com.example.tbcacademy.presentation.products.contract.ProductsUiState
import com.example.tbcacademy.presentation.products.mapper.toUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val getCategories: GetCategoriesUseCase,
    private val getProducts: GetProductsUseCase
) : BaseViewModel<ProductsUiState, ProductsEvent, ProductsSideEffect>(
    initialState = ProductsUiState()
) {

    init {
        loadData()
    }

    override fun onEvent(event: ProductsEvent) {
        when (event) {
            is ProductsEvent.CategorySelected -> {
                updateState {
                    it.copy(selectedCategory = event.category)
                }
            }
        }
    }

    private fun loadData() {
        viewModelScope.launch {
            val categories = getCategories().map { it.toUi() }
            val products = getProducts().map { it.toUi() }

            updateState {
                it.copy(
                    categories = categories,
                    products = products,
                    isLoading = false
                )
            }
        }
    }
}