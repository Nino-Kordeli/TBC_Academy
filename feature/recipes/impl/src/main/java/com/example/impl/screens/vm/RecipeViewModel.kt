package com.example.impl.screens.vm

import androidx.lifecycle.viewModelScope
import com.example.common.resource.Resource
import com.example.domain.RecipeRepository
import com.example.impl.screens.contract.RecipeEvent
import com.example.impl.screens.contract.RecipeSideEffect
import com.example.impl.screens.contract.RecipeState
import com.example.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository
) : BaseViewModel<RecipeState, RecipeEvent, RecipeSideEffect>(RecipeState()) {

    init {
        loadRecipes()
    }

    override fun onEvent(event: RecipeEvent) {}  // no events for now

    private fun loadRecipes() {
        viewModelScope.launch {
            recipeRepository.getRecipes().collect { resource ->
                when (resource) {
                    is Resource.Loading -> updateState { it.copy(isLoading = resource.loading) }
                    is Resource.Error -> updateState { it.copy(error = resource.errorMessage, isLoading = false) }
                    is Resource.Success -> updateState { it.copy(categories = resource.data, isLoading = false, error = null) }
                }
            }
        }
    }
}