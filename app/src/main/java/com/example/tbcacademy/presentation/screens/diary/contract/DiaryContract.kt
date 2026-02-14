package com.example.tbcacademy.presentation.screens.diary.contract

import com.example.tbcacademy.domain.model.food.Food
import com.example.tbcacademy.domain.model.food.MealType

data class DiaryState(
    val breakfast: List<Food> = emptyList(),
    val lunch: List<Food> = emptyList(),
    val dinner: List<Food> = emptyList(),
    val snacks: List<Food> = emptyList()
)

sealed interface DiaryEvent {

    data class AddFood(
        val mealType : MealType,
        val food: Food
    ) : DiaryEvent
}

