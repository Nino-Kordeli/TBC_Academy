package com.example.tbcacademy.presentation.search.vm

import androidx.lifecycle.viewModelScope
import com.example.tbcacademy.common.BaseViewModel
import com.example.tbcacademy.data.common.Resource
import com.example.tbcacademy.domain.usecase.ingredient.GetIngredientsUseCase
import com.example.tbcacademy.presentation.recipe_details.mapper.toUi
import com.example.tbcacademy.presentation.search.contract.SearchContract.SearchEvent
import com.example.tbcacademy.presentation.search.contract.SearchContract.SearchSideEffect
import com.example.tbcacademy.presentation.search.contract.SearchContract.SearchState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getIngredientsUseCase: GetIngredientsUseCase,
) : BaseViewModel<SearchState, SearchEvent, SearchSideEffect>(SearchState()) {

    override fun onEvent(event: SearchEvent) {
        when (event) {
            SearchEvent.LoadIngredients -> loadIngredients()
        }
    }

    private fun loadIngredients() = viewModelScope.launch {
        getIngredientsUseCase.invoke().collect {
            when (it) {
                is Resource.Error -> updateState { currentState ->
                    currentState.copy(error = it.errorMessage)
                }

                is Resource.Loading -> {
                    updateState { currentState -> currentState.copy(isLoading = it.loading) }
                }

                is Resource.Success -> {
                    updateState { currentState -> currentState.copy(ingredients = it.data.toUi()) }
                }
            }
        }
    }
}
