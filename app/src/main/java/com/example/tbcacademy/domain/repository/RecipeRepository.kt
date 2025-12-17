package com.example.tbcacademy.domain.repository

import com.example.tbcacademy.data.common.Resource
import com.example.tbcacademy.domain.model.Recipe
import com.example.tbcacademy.domain.model.RecipeDetail
import kotlinx.coroutines.flow.Flow

interface RecipeRepository {
    suspend fun getTrendingRecipes(): Flow<Resource<List<Recipe>>>
    suspend fun getRecipesByIngredient(ingredientId: Int): Flow<Resource<List<Recipe>>>
    suspend fun getRecipeDetails(): Flow<Resource<List<RecipeDetail>>>
}
