package com.example.impl.screens.add_food_details.contract

import com.example.domain.model.food.Food
import com.example.model.MealType

data class AddFoodDetailsState(
    val food: Food? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val numberOfServings: Int = 1,
    val mealType: MealType = MealType.BREAKFAST
)

sealed class AddFoodDetailsEvent {
    data class LoadFood(val food: Food, val mealType: MealType) : AddFoodDetailsEvent()
    data class ServingsChanged(val servings: Int) : AddFoodDetailsEvent()
    object SaveFood : AddFoodDetailsEvent()
}

sealed class AddFoodDetailsSideEffect {
    data object NavigateBack : AddFoodDetailsSideEffect()
}