package com.example.domain.repository

import com.example.domain.model.food.LoggedFood
import com.example.model.MealType
import kotlinx.coroutines.flow.Flow

interface DiaryRepository {

    fun getFoodsForMeal(mealType: MealType): Flow<List<LoggedFood>>

    suspend fun addFood(
        mealType: MealType,
        food: LoggedFood
    )

    suspend fun removeFood(
        mealType: MealType,
        food: LoggedFood
    )

    suspend fun resetDailyData()
}