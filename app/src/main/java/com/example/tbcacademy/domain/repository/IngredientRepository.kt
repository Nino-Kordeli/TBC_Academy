package com.example.tbcacademy.domain.repository

import com.example.tbcacademy.data.common.Resource
import com.example.tbcacademy.domain.model.Ingredient
import kotlinx.coroutines.flow.Flow

interface IngredientRepository {
    suspend fun getIngredients(): Flow<Resource<List<Ingredient>>>
}
