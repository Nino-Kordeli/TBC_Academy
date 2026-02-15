package com.example.data.mapper

import com.example.data.dto.FoodDto
import com.example.domain.model.food.Food

fun FoodDto.toDomain(): Food {
    return Food(
        id = id,
        name = name,
        calories = calories,
        carbs = carbs,
        fat = fat,
        protein = protein,
        isMeal = isMeal,
    )
}
