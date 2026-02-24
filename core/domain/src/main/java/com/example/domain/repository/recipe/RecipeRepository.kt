package com.example.domain.repository.recipe

import com.example.common.resource.Resource
import com.example.domain.model.recipe.RecipeCategory
import kotlinx.coroutines.flow.Flow

interface RecipeRepository {
    fun getRecipes(): Flow<Resource<List<RecipeCategory>>>
}