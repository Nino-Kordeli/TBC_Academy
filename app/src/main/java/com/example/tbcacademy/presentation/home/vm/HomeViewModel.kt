package com.example.tbcacademy.presentation.home.vm

import androidx.lifecycle.viewModelScope
import com.example.tbcacademy.common.BaseViewModel
import com.example.tbcacademy.data.common.Resource
import com.example.tbcacademy.domain.usecase.recipe.GetTrendingRecipesUseCase
import com.example.tbcacademy.presentation.home.contract.HomeEvent
import com.example.tbcacademy.presentation.home.contract.HomeSideEffect
import com.example.tbcacademy.presentation.home.contract.HomeSideEffect.*
import com.example.tbcacademy.presentation.home.contract.HomeState
import com.example.tbcacademy.presentation.recipe_details.mapper.toUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getTrendingRecipesUseCase: GetTrendingRecipesUseCase,
) : BaseViewModel<HomeState, HomeEvent, HomeSideEffect>(HomeState()) {

    init {
        onEvent(HomeEvent.LoadTrendingRecipes)
    }

    override fun onEvent(event: HomeEvent) {
        when (event) {
            HomeEvent.LoadTrendingRecipes -> loadTrendingRecipes()
            HomeEvent.Retry -> loadTrendingRecipes()
            is HomeEvent.OnRecipeClick -> {
                emitSideEffect(NavigateToRecipe(event.id))
            }

            HomeEvent.NavigateToProfile -> {
                emitSideEffect(HomeSideEffect.NavigateToProfile)
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