package com.example.tbcacademy.domain.repository

import com.example.tbcacademy.data.common.Resource
import com.example.tbcacademy.domain.model.Recipe
import kotlinx.coroutines.flow.Flow

interface FirestoreRepository {
    suspend fun saveFavourite(request: Recipe)
    fun getFavourites(): Flow<Resource<List<Recipe>>>
    suspend fun removeFavourite(recipeId: Int)
}