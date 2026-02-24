package com.example.domain.usecase.food

import com.example.domain.model.food.Food
import com.example.domain.model.food.LoggedFood
import com.example.domain.repository.user_preferences.UserPreferencesRepository
import com.example.model.MealType
import kotlinx.coroutines.flow.first
import java.util.UUID
import javax.inject.Inject

class LogFoodUseCase @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository
) {
    suspend operator fun invoke(food: Food, grams: Int, mealType: MealType) {
        val multiplier = grams / 100f

        val userId = userPreferencesRepository.getCurrentUserId().first()

        if (userId.isNullOrBlank()) {
            throw Exception("User not logged in")
        }

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
            mealType = mealType,
            userId = userId
        )

        val allFoods = userPreferencesRepository
            .getFoodsForMeal(mealType)
            .first()

        userPreferencesRepository.saveFoodsForMeal(
            mealType,
            allFoods + loggedFood
        )
    }
}