package com.example.impl.screens.diary.model

import com.example.domain.model.food.Food

data class FoodModel(
    val breakfast: List<Food>,
    val lunch: List<Food>,
    val dinner: List<Food>,
    val snacks: List<Food>,
    val consumedCalories: Int
)
