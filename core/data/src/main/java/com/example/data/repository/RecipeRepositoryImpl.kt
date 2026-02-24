package com.example.data.repository

import com.example.common.resource.Resource
import com.example.data.mapper.toDomain
import com.example.data.remote.FoodApi
import com.example.domain.RecipeRepository
import com.example.domain.model.recipe.RecipeCategory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RecipeRepositoryImpl @Inject constructor(
    private val recipeApi: FoodApi
) : RecipeRepository {
    override fun getRecipes(): Flow<Resource<List<RecipeCategory>>> = flow {
        emit(Resource.Loading(true))
        try {
            val response = recipeApi.getRecipes()
            if (response.isSuccessful) {
                val categories = response.body()!!.categories.map { it.toDomain() }
                emit(Resource.Loading(false))
                emit(Resource.Success(categories))
            } else {
                emit(Resource.Loading(false))
                emit(Resource.Error("Failed to load recipes: ${response.code()}"))
            }
        } catch (e: Exception) {
            emit(Resource.Loading(false))
            emit(Resource.Error(e.message ?: "Unknown error"))
        }
    }
}