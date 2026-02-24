package com.example.impl.screens.add_food.contract

import com.example.domain.model.food.Food

data class AddFoodState(
    val query: String = "",
    val foodList: List<Food> = emptyList()
)

sealed interface AddFoodEvent {
    data class Search(val query: String) : AddFoodEvent
}

sealed interface AddFoodSideEffect {
    data class ShowError(val message: String) : AddFoodSideEffect
}