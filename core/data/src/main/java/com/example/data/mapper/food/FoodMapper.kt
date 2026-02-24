package com.example.data.mapper.food

import com.example.data.dto.food.FoodDto
import com.example.data.dto.food.FoodResponseDto
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