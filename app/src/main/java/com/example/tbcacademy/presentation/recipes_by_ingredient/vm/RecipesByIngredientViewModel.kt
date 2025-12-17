package com.example.tbcacademy.presentation.recipes_by_ingredient.vm

import androidx.lifecycle.viewModelScope
import com.example.tbcacademy.common.BaseViewModel
import com.example.tbcacademy.data.common.Resource
import com.example.tbcacademy.domain.usecase.recipe.GetRecipesByIngredientUseCase
import com.example.tbcacademy.presentation.mapper.toUi
import com.example.tbcacademy.presentation.recipes_by_ingredient.contract.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipesByIngredientViewModel @Inject constructor(
    private val getRecipesByIngredientUseCase: GetRecipesByIngredientUseCase
) : BaseViewModel<
        RecipesByIngredientState,
        RecipesByIngredientEvent,
        RecipesByIngredientSideEffect>(
    RecipesByIngredientState()
) {

    override fun onEvent(event: RecipesByIngredientEvent) {
        when (event) {
            is RecipesByIngredientEvent.LoadRecipes ->
                load(event.ingredientId)
        }
    }

    private fun load(ingredientId: Int) {
        viewModelScope.launch {
            getRecipesByIngredientUseCase(ingredientId).collect { res ->
                when (res) {
                    is Resource.Success ->
                        updateState { it.copy(recipes = res.data.toUi()) }

                    is Resource.Loading ->
                        updateState { it.copy(isLoading = res.loading) }

                    is Resource.Error ->
                        emitSideEffect(
                            RecipesByIngredientSideEffect.ShowError(res.errorMessage)
                        )
                }
            }
        }
    }
}
