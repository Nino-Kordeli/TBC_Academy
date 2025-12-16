package com.example.tbcacademy.data.repository

import com.example.tbcacademy.data.api.RecipesApi
import com.example.tbcacademy.domain.repository.RecipeRepository
import com.example.tbcacademy.presentation.model.RecipeUi
import javax.inject.Inject

class RecipeRepositoryImpl @Inject constructor(
    private val api: RecipesApi
) : RecipeRepository {

    override suspend fun getTrendingRecipes(): List<RecipeUi> {
        val result = api.getRecipes()
        return result
    }
}