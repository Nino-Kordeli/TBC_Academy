package com.example.impl.screens.add_food_details.contract

import com.example.domain.model.food.Food
import com.example.impl.screens.add_food_details.model.NutritionUiModel

data class AddFoodDetailsState(
    val foodState: Boolean = true,
    val nutrition: NutritionUiModel = NutritionUiModel(
        calorieGoal = 0,
        consumedCalories = 0,
        carbs = 0f,
        fat = 0f,
        protein = 0f
    ),
    val foodList: List<Food> = emptyList()
)

sealed class AddFoodDetailsSideEffect {

}

sealed class AddFoodDetailsEvent {
    data object FetchFoods : AddFoodDetailsEvent()
}