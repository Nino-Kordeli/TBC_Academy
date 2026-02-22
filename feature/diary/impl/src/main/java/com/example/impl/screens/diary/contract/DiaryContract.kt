package com.example.impl.screens.diary.contract

import com.example.domain.model.food.Food
import com.example.model.MealType

data class DiaryState(
    val breakfast: List<Food> = emptyList(),
    val lunch: List<Food> = emptyList(),
    val dinner: List<Food> = emptyList(),
    val snacks: List<Food> = emptyList(),
    val goalCalories: Int = 2000,
    val consumedCalories: Int = 0,
    val exerciseCalories: Int = 0
) {
    val remainingCalories: Int
        get() = goalCalories - consumedCalories + exerciseCalories
}

sealed interface DiaryEvent
sealed interface DiarySideEffect

data class AddFood(
    val mealType: MealType,
    val food: Food
) : DiaryEvent