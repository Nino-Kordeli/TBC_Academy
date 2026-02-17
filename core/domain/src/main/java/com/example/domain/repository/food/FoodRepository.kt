package com.example.domain.repository.food

import com.example.domain.model.food.Food

interface FoodRepository {
    suspend fun searchFoods(query: String): List<Food>
    suspend fun getFoodById(id: String): Food?
    fun getAllFoods(): List<Food>
}