package com.example.tbcacademy.presentation.search.vm

import androidx.lifecycle.viewModelScope
import com.example.tbcacademy.common.Resource
import com.example.tbcacademy.domain.usecase.GetCategoriesUseCase
import com.example.tbcacademy.presentation.common.BaseViewModel
import com.example.tbcacademy.presentation.search.contract.CategoryEvent
import com.example.tbcacademy.presentation.search.contract.CategorySideEffect
import com.example.tbcacademy.presentation.search.contract.CategoryState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase,
) : BaseViewModel<CategoryState, CategoryEvent, CategorySideEffect>(CategoryState()) {

    private val searchQuery = MutableStateFlow("")

    init {
        observeSearch()
    }

    override fun onEvent(event: CategoryEvent) {
        when (event) {
            is CategoryEvent.SearchQueryChanged -> searchQuery.value = event.query
        }
    }

    @OptIn(FlowPreview::class)
    private fun observeSearch() {
        searchQuery
            .debounce(500)
            .distinctUntilChanged()
            .onEach { loadCategories(it) }
            .launchIn(viewModelScope)
    }

    private fun loadCategories(query: String) {
        viewModelScope.launch {
            updateState { it.copy(isLoading = true) }

            when (val result = getCategoriesUseCase(query)) {
                is Resource.Success -> {
                    updateState {
                        it.copy(
                            isLoading = false,
                            categories = result.data,
                            error = null
                        )
                    }
                }

                is Resource.Error -> {
                    updateState { it.copy(isLoading = false) }
                    emitSideEffect(CategorySideEffect.ShowError(result.errorMessage))
                }

                is Resource.Loader -> {
                    updateState { it.copy(isLoading = result.isLoading) }
                }
            }
        }
    }
}