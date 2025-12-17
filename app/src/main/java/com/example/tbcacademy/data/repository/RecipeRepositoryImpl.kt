package com.example.tbcacademy.data.repository

import com.example.tbcacademy.data.api.RecipesApi
import com.example.tbcacademy.data.common.HandleResponse
import com.example.tbcacademy.data.common.Resource
import com.example.tbcacademy.data.mapper.base.asResource
import com.example.tbcacademy.data.mapper.detailToDomain
import com.example.tbcacademy.data.mapper.recipeToDomain
import com.example.tbcacademy.domain.model.Recipe
import com.example.tbcacademy.domain.model.RecipeDetail
import com.example.tbcacademy.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RecipeRepositoryImpl @Inject constructor(
    private val api: RecipesApi,
    private val handleResponse: HandleResponse,
) : RecipeRepository {

    override suspend fun getTrendingRecipes(): Flow<Resource<List<Recipe>>> {
        return handleResponse.apiCall { api.getRecipes() }.asResource { it.recipeToDomain() }
    }

    override suspend fun getRecipesByIngredient(
        ingredientId: Int
    ): Flow<Resource<List<Recipe>>> {
        return handleResponse.apiCall { api.getRecipeDetails() }
            .asResource { details ->
                details
                    .detailToDomain()
                    .filter { ingredientId in it.ingredientIds }
                    .map {
                        Recipe(
                            id = it.id,
                            name = it.name,
                            imageUrl = it.imageUrl
                        )
                    }
            }
    }

    override suspend fun getRecipeDetails(): Flow<Resource<List<RecipeDetail>>> {
        return handleResponse.apiCall { api.getRecipeDetails() }.asResource { it.detailToDomain() }
    }
}