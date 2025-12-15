package com.example.tbcacademy.domain.repository

import com.example.tbcacademy.domain.model.Recipe

interface RecipeRepository {
    suspend fun getTrendingRecipes(): List<Recipe>
}
