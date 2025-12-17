package com.example.tbcacademy.domain.usecase.recipe

import com.example.tbcacademy.domain.repository.RecipeRepository
import com.example.tbcacademy.data.common.Resource
import com.example.tbcacademy.domain.model.Recipe
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRecipesByIngredientUseCase @Inject constructor(
    private val repository: RecipeRepository
) {
    suspend operator fun invoke(
        ingredientId: Int
    ): Flow<Resource<List<Recipe>>> {
        return repository.getRecipesByIngredient(ingredientId)
    }
}
