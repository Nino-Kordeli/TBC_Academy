package com.example.tbcacademy.presentation.fragment.home.vm

import androidx.lifecycle.viewModelScope
import com.example.tbcacademy.common.BaseViewModel
import com.example.tbcacademy.domain.repository.RecipeRepository
import com.example.tbcacademy.presentation.fragment.home.contract.HomeContract
import com.example.tbcacademy.presentation.mapper.toUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository,
) : BaseViewModel<
        HomeContract.HomeState,
        HomeContract.HomeEvent,
        HomeContract.HomeSideEffect
        >(HomeContract.HomeState()) {

    init {
        onEvent(HomeContract.HomeEvent.LoadTrendingRecipes)
    }

    override fun onEvent(event: HomeContract.HomeEvent) {
        when (event) {
            HomeContract.HomeEvent.LoadTrendingRecipes -> loadTrendingRecipes()
            HomeContract.HomeEvent.Retry -> loadTrendingRecipes()
            is HomeContract.HomeEvent.OnRecipeClick -> {
                emitSideEffect(HomeContract.HomeSideEffect.NavigateToRecipe(event.id))
            }
        }
    }

    private fun loadTrendingRecipes() = viewModelScope.launch {
        updateState { it.copy(isLoading = true, error = null) }

        try {
            val recipes = recipeRepository.getTrendingRecipes().toUi()
            updateState { it.copy(isLoading = false, recipes = recipes) }
        } catch (e: Exception) {
            updateState { it.copy(isLoading = false, error = e.message ?: "Unknown error") }
            emitSideEffect(HomeContract.HomeSideEffect.ShowError(e.message ?: "Unknown error"))
        }
    }
}
