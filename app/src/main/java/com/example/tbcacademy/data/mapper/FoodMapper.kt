package com.example.tbcacademy.data.mapper

import com.example.tbcacademy.data.dto.FoodDto
import com.example.tbcacademy.domain.model.food.Food

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