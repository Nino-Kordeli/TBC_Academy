package com.example.tbcacademy.domain.usecase

import com.example.tbcacademy.domain.model.food.Food
import com.example.tbcacademy.domain.model.food.LoggedFood
import java.util.UUID
import javax.inject.Inject

/*
class LogFoodUseCase @Inject constructor(
    private val diaryRepository: DiaryRepository
) {

    suspend operator fun invoke(food: Food, grams: Int) {
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
        diaryRepository.insert(loggedFood)
    }
}*/
