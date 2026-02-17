package com.example.impl.screens.diary.contract

import com.example.domain.model.food.Food
import com.example.domain.model.food.MealType

data class DiaryState(
    val breakfast: List<Food> = emptyList(),
    val lunch: List<Food> = emptyList(),
    val dinner: List<Food> = emptyList(),
    val snacks: List<Food> = emptyList(),
    val goalCalories: Int = 0
)

sealed interface DiaryEvent {

    data class AddFood(
        val mealType: MealType,
        val food: Food
    ) : DiaryEvent
}