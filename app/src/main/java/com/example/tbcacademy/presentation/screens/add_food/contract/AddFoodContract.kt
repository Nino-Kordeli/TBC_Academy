package com.example.tbcacademy.presentation.screens.add_food.contract

import com.example.tbcacademy.domain.model.food.Food

data class AddFoodState(
    val query: String = "",
    val foods: List<Food> = emptyList()
)

sealed interface AddFoodEvent {
    data class Search(val query: String) : AddFoodEvent
}