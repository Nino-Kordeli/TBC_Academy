package com.example.domain.usecase

import com.example.domain.model.food.Food
import com.example.domain.model.food.LoggedFood
import com.example.domain.model.food.MealType
import java.util.UUID
import javax.inject.Inject

class LogFoodUseCase @Inject constructor(
    private val userPreferences: UserPreferencesManager
) {
    suspend operator fun invoke(food: Food, grams: Int, mealType: MealType) {
        val multiplier = grams / 100f

        val loggedFood = LoggedFood(
            id = UUID.randomUUID().toString(),
            foodId = food.id,
            name = food.name,
            amountGrams = grams,
            calories = (food.calories * multiplier).toInt(),
            carbs = food.carbs * multiplier,
            fat = food.fat * multiplier,
            protein = food.protein * multiplier,
            timestamp = System.currentTimeMillis(),
        )

        val currentFoods = userPreferences.getFoodsForMeal(mealType).first()
        val updateFoods = currentFoods + loggedFood
        userPreferences.saveFoodsForMeal(mealType, updateFoods)
    }
}