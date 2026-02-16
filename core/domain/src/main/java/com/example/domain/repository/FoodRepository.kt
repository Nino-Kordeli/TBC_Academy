package com.example.domain.repository

import com.example.domain.model.food.Food

interface FoodRepository {
    suspend fun searchFoods(query: String): List<Food>
    suspend fun getFoodById(id: String): Food?
}
