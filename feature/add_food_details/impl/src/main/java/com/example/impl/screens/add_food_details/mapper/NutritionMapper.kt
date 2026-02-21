package com.example.impl.screens.add_food_details.mapper

import com.example.domain.model.food.Food
import com.example.impl.screens.add_food_details.model.NutritionUiModel

fun List<Food>.toNutritionUiModel(goal: Int): NutritionUiModel {
    return NutritionUiModel(
        calorieGoal = goal,
        consumedCalories = sumOf { it.calories },
        carbs = sumOf { it.carbs.toDouble() }.toFloat(),
        fat = sumOf { it.fat.toDouble() }.toFloat(),
        protein = sumOf { it.protein.toDouble() }.toFloat()
    )
}