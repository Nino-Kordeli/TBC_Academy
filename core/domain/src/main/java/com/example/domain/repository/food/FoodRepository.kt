package com.example.domain.repository.food

import com.example.domain.model.food.Food
import com.example.common.resource.Resource
import kotlinx.coroutines.flow.Flow

interface FoodRepository {
    suspend fun getAllFoods(): Flow<Resource<List<Food>>>
//    suspend fun searchFoods(query: String): List<Food>
//    suspend fun getFoodById(id: String): Food?
}