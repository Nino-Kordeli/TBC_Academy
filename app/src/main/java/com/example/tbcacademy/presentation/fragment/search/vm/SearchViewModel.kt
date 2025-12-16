package com.example.tbcacademy.presentation.fragment.search.vm

import androidx.lifecycle.viewModelScope
import com.example.tbcacademy.common.BaseViewModel
import com.example.tbcacademy.domain.repository.IngredientRepository
import com.example.tbcacademy.presentation.fragment.search.contract.SearchContract
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: IngredientRepository,
) : BaseViewModel<SearchContract.SearchState, SearchContract.SearchEvent, SearchContract.SearchSideEffect>(
    SearchContract.SearchState()
) {

    override fun onEvent(event: SearchContract.SearchEvent) {
        when (event) {
            SearchContract.SearchEvent.LoadIngredients -> loadIngredients()
        }
    }

    private fun loadIngredients() = viewModelScope.launch {
        val ingredients = repository.getIngredients()
        updateState { it.copy(ingredients = ingredients) }
    }
}
