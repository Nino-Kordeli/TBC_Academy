package com.example.tbcacademy.presentation.home.vm

import androidx.lifecycle.viewModelScope
import com.example.tbcacademy.common.BaseViewModel
import com.example.tbcacademy.data.common.Resource
import com.example.tbcacademy.domain.usecase.firestore.GetFavouriteUseCase
import com.example.tbcacademy.domain.usecase.firestore.RemoveFavouriteUseCase
import com.example.tbcacademy.domain.usecase.firestore.SaveFavouriteUseCase
import com.example.tbcacademy.domain.usecase.recipe.GetTrendingRecipesUseCase
import com.example.tbcacademy.presentation.home.contract.HomeEvent
import com.example.tbcacademy.presentation.home.contract.HomeSideEffect
import com.example.tbcacademy.presentation.home.contract.HomeSideEffect.*
import com.example.tbcacademy.presentation.home.contract.HomeState
import com.example.tbcacademy.presentation.mapper.toDomainRecipe
import com.example.tbcacademy.presentation.mapper.toUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getTrendingRecipesUseCase: GetTrendingRecipesUseCase,
    private val saveFavouriteUseCase: SaveFavouriteUseCase,
    private val getFavouriteUseCase: GetFavouriteUseCase,
    private val removeFavouriteUseCase: RemoveFavouriteUseCase
) : BaseViewModel<HomeState, HomeEvent, HomeSideEffect>(HomeState()) {

    override fun onEvent(event: HomeEvent) {
        when (event) {
            HomeEvent.LoadTrendingRecipes -> loadTrendingRecipes()
            HomeEvent.Retry -> loadTrendingRecipes()
            is HomeEvent.OnRecipeClick -> {
                emitSideEffect(NavigateToRecipe(event.id))
            }

            HomeEvent.NavigateToProfile -> {
                emitSideEffect(NavigateToProfile)
            }

            is HomeEvent.OnFavoriteClick -> {
                toggleFavourite(event.id)
            }

            HomeEvent.OnFavouriteIconClick -> emitSideEffect(HomeSideEffect.NavigateToFavourites)
        }
    }

    private fun toggleFavourite(recipeId: Int) = viewModelScope.launch {
        val currentRecipe = state.value.recipes.first { it.id == recipeId }

        if (currentRecipe.isFavourite) {
            removeFavouriteUseCase(recipeId)
        } else {
            saveFavouriteUseCase.invoke(currentRecipe.toDomainRecipe())
        }

        updateState { currentState ->
            currentState.copy(
                recipes = currentState.recipes.map { recipe ->
                    if (recipe.id == recipeId) {
                        recipe.copy(isFavourite = !recipe.isFavourite)
                    } else recipe
                }
            )
        }

        getFavouriteRecipes()
    }

    private fun getFavouriteRecipes() = viewModelScope.launch {
        getFavouriteUseCase.invoke().collect { it ->
            when (it) {
                is Resource.Error -> updateState { currentState ->
                    currentState.copy(error = it.errorMessage)
                }

                is Resource.Loading -> {
                    updateState { currentState -> currentState.copy(isLoading = it.loading) }
                }

                is Resource.Success -> {
                    updateState { currentState ->

                        val favouriteIds = it.data.map { recipe -> recipe.id }.toSet()

                        val updatedRecipes = currentState.recipes.map { recipeUi ->
                            recipeUi.copy(
                                isFavourite = recipeUi.id in favouriteIds
                            )
                        }

                        currentState.copy(
                            recipes = updatedRecipes,
                            favouriteCount = updatedRecipes.count { it.isFavourite }
                        )
                    }
                }
            }
        }
    }

    private fun loadTrendingRecipes() = viewModelScope.launch {
        getTrendingRecipesUseCase.invoke().collect {
            when (it) {
                is Resource.Error -> updateState { currentState ->
                    currentState.copy(error = it.errorMessage)
                }

                is Resource.Loading -> {
                    updateState { currentState -> currentState.copy(isLoading = it.loading) }
                }

                is Resource.Success -> {
                    updateState { currentState -> currentState.copy(recipes = it.data.toUi()) }
                }
            }
        }
    }
}