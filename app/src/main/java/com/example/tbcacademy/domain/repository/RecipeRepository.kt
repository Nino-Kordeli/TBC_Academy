package com.example.tbcacademy.domain.repository

import com.example.tbcacademy.presentation.model.RecipeUi

interface RecipeRepository {
    suspend fun getTrendingRecipes(): List<RecipeUi>
}
