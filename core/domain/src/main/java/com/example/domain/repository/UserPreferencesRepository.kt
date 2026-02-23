package com.example.domain.repository

import com.example.domain.model.food.LoggedFood
import com.example.model.MealType
import kotlinx.coroutines.flow.Flow

interface UserPreferencesRepository {
    suspend fun saveGoalCalories(calories: Int)
    suspend fun setCurrentUserId(userId: String?)
    fun getGoalCalories(): Flow<Int>
    suspend fun saveUserProfile(
        email: String,
        name: String?,
        weight: String?,
        height: String?,
        age: Int?,
        gender: String?
    )
    fun getUserEmail(): Flow<String?>
    suspend fun checkAndResetDailyData()
    suspend fun saveFoodsForMeal(mealType: MealType, foods: List<LoggedFood>)
    fun getFoodsForMeal(mealType: MealType): Flow<List<LoggedFood>>
    suspend fun addFood(mealType: MealType, food: LoggedFood)
    suspend fun clearOnlyDailyFoodLogs()
    suspend fun clearAllUserData()
    fun getCurrentUserId(): Flow<String?>
}