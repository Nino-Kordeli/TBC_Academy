package com.example.tbcacademy.presentation.saved_recipes.vm

import androidx.lifecycle.viewModelScope
import com.example.tbcacademy.common.BaseViewModel
import com.example.tbcacademy.data.common.Resource
import com.example.tbcacademy.domain.usecase.firestore.GetFavouriteUseCase
import com.example.tbcacademy.domain.usecase.firestore.RemoveFavouriteUseCase
import com.example.tbcacademy.domain.usecase.firestore.SaveFavouriteUseCase
import com.example.tbcacademy.presentation.mapper.toDomainRecipe
import com.example.tbcacademy.presentation.mapper.toUi
import com.example.tbcacademy.presentation.saved_recipes.contract.SavedRecipesEvent
import com.example.tbcacademy.presentation.saved_recipes.contract.SavedRecipesSideEffect
import com.example.tbcacademy.presentation.saved_recipes.contract.SavedRecipesState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SavedRecipesViewModel @Inject constructor(
    private val getFavouriteUseCase: GetFavouriteUseCase,
    private val saveFavouriteUseCase: SaveFavouriteUseCase,
    private val removeFavouriteUseCase: RemoveFavouriteUseCase
) : BaseViewModel<SavedRecipesState, SavedRecipesEvent, SavedRecipesSideEffect>(
    SavedRecipesState()
) {

    init { loadFavourites() }

    override fun onEvent(event: SavedRecipesEvent) {
        when (event) {
            is SavedRecipesEvent.OnRecipeClick -> emitSideEffect(SavedRecipesSideEffect.NavigateToRecipe(event.id))
            is SavedRecipesEvent.OnFavoriteClick -> toggleFavourite(event.id)
        }
    }

    private fun loadFavourites() = viewModelScope.launch {
        getFavouriteUseCase.invoke().collectLatest { resource ->
            when (resource) {
                is Resource.Success -> updateState {
                    it.copy(
                        recipes = resource.data.toUi(),
                        favouriteCount = resource.data.size,
                        isLoading = false
                    )
                }
                is Resource.Loading -> updateState { it.copy(isLoading = resource.loading) }
                is Resource.Error -> updateState { it.copy(error = resource.errorMessage) }
            }
        }
    }

    private fun toggleFavourite(recipeId: Int) = viewModelScope.launch {
        val recipe = state.value.recipes.first { it.id == recipeId }

        if (recipe.isFavourite) removeFavouriteUseCase(recipeId)
        else saveFavouriteUseCase(recipe.toDomainRecipe())

        loadFavourites()
    }
}
