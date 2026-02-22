package com.example.impl.screens.add_food_details.contract

import com.example.domain.model.food.Food
import com.example.impl.screens.add_food_details.model.NutritionUiModel
import com.example.model.MealType

data class AddFoodDetailsState(
    val food: Food? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val numberOfServings: Int = 1,
    val mealType: MealType = MealType.BREAKFAST,
    val nutrition: NutritionUiModel = NutritionUiModel(
        calorieGoal = 0,
        consumedCalories = 0,
        carbs = 0f,
        fat = 0f,
        protein = 0f
    )
)

sealed class AddFoodDetailsSideEffect {
    data object NavigateBack : AddFoodDetailsSideEffect()
}

sealed class AddFoodDetailsEvent {
    data class LoadFood(val food: Food) : AddFoodDetailsEvent()
    data class ServingsChanged(val servings: Int) : AddFoodDetailsEvent()
    object SaveFood : AddFoodDetailsEvent()
}