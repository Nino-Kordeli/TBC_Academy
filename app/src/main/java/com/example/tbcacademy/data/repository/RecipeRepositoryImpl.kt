package com.example.tbcacademy.data.repository

import com.example.tbcacademy.data.remote.service.RecipeApi
import com.example.tbcacademy.domain.model.Recipe
import com.example.tbcacademy.domain.repository.RecipeRepository
import javax.inject.Inject

class RecipeRepositoryImpl @Inject constructor(
    private val api: RecipeApi,
): RecipeRepository {
    override suspend fun getTrendingRecipes(): List<Recipe> {
        TODO("Not yet implemented")
    }
}