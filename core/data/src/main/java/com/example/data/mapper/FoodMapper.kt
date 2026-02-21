package com.example.data.mapper

import com.example.data.dto.FoodDto
import com.example.data.dto.FoodResponseDto
import com.example.domain.model.food.Food

fun FoodResponseDto.toDomain() = this.foods.map { it.toDomain() }

fun FoodDto.toDomain(): Food {
    return Food(
        id = id,
        name = name,
        calories = calories,
        carbs = carbs,
        fat = fat,
        protein = protein,
        isMeal = isMeal
    )
}