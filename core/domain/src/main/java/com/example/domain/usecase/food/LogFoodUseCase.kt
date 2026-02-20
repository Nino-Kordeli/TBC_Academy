package com.example.domain.usecase.food

import com.example.domain.model.food.Food
import com.example.domain.model.food.LoggedFood
import com.example.model.MealType
import com.example.domain.repository.UserPreferencesRepository
import kotlinx.coroutines.flow.first
import java.util.UUID
import javax.inject.Inject

//class LogFoodUseCase @Inject constructor(
//    private val userPreferencesRepository: UserPreferencesRepository
//) {
//    suspend operator fun invoke(food: Food, grams: Int, mealType: MealType) {
//        val multiplier = grams / 100f
//
//        val loggedFood = LoggedFood(
//            id = UUID.randomUUID().toString(),
//            foodId = food.id,
//            name = food.name,
//            amountGrams = grams,
//            calories = (food.calories * multiplier).toInt(),
//            carbs = food.carbs * multiplier,
//            fat = food.fat * multiplier,
//            protein = food.protein * multiplier,
//            timestamp = System.currentTimeMillis()
//        )
//
//        val currentFoods = userPreferencesRepository.getFoodsForMeal(mealType).first()
//        val updatedFoods = currentFoods + loggedFood
//        userPreferencesRepository.saveFoodsForMeal(mealType, updatedFoods)
//    }
//}