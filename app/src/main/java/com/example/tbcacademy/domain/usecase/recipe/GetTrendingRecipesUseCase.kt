package com.example.tbcacademy.domain.usecase.recipe

import com.example.tbcacademy.domain.repository.RecipeRepository
import javax.inject.Inject

class GetTrendingRecipesUseCase @Inject constructor(
    private val repository: RecipeRepository
) {
    suspend operator fun invoke() = repository.getTrendingRecipes()
}