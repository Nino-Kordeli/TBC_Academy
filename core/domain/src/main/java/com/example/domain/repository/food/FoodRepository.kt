package com.example.domain.repository.food

import com.example.common.resource.Resource
import com.example.domain.model.food.Food
import kotlinx.coroutines.flow.Flow

interface FoodRepository {
    suspend fun getAllFoods(): Flow<Resource<List<Food>>>
}