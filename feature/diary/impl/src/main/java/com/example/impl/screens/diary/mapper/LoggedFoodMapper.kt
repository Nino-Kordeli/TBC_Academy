package com.example.impl.screens.diary.mapper

import com.example.domain.model.food.Food
import com.example.domain.model.food.LoggedFood

internal fun LoggedFood.toFood(): Food {
    return Food(
        id = this.foodId,
        name = this.name,
        calories = this.calories,
        carbs = this.carbs,
        fat = this.fat,
        protein = this.protein,
        isMeal = false
    )
}
