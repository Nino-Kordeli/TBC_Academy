package com.example.impl.screens.diary.vm

import androidx.lifecycle.viewModelScope
import com.example.domain.model.food.Food
import com.example.domain.model.food.LoggedFood
import com.example.domain.repository.UserPreferencesRepository
import com.example.impl.screens.diary.contract.DiaryEvent
import com.example.impl.screens.diary.contract.DiarySideEffect
import com.example.impl.screens.diary.contract.DiaryState
import com.example.model.MealType
import com.example.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiaryViewModel @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository
) : BaseViewModel<DiaryState, DiaryEvent, DiarySideEffect>(
    initialState = DiaryState()
) {

    init {
        checkAndReset()
        loadSavedFood()
        loadGoalCalories()
    }

    override fun onEvent(event: DiaryEvent) {
    }

    private fun checkAndReset() {
        viewModelScope.launch {
            userPreferencesRepository.checkAndResetDailyData()
        }
    }

    private fun loadGoalCalories() {
        viewModelScope.launch {
            userPreferencesRepository.getGoalCalories().collect { goalCalories ->
                updateState { it.copy(goalCalories = goalCalories) }
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun loadSavedFood() {
        viewModelScope.launch {
            userPreferencesRepository.getCurrentUserId()
                .flatMapLatest { userId ->
                    if (userId == null) {
                        flowOf(null)
                    } else {
                        combine(
                            foodForUser(MealType.BREAKFAST, userId),
                            foodForUser(MealType.LUNCH, userId),
                            foodForUser(MealType.DINNER, userId),
                            foodForUser(MealType.SNACKS, userId)
                        ) { breakfast, lunch, dinner, snacks ->
                            val totalConsumed = breakfast.sumOf { it.calories } +
                                    lunch.sumOf { it.calories } +
                                    dinner.sumOf { it.calories } +
                                    snacks.sumOf { it.calories }

                            FoodData(
                                breakfast = breakfast.map { it.toFood() },
                                lunch = lunch.map { it.toFood() },
                                dinner = dinner.map { it.toFood() },
                                snacks = snacks.map { it.toFood() },
                                consumedCalories = totalConsumed
                            )
                        }
                    }
                }
                .collect { foodData ->
                    if (foodData == null) {
                        updateState { it.copy(
                            breakfast = emptyList(),
                            lunch = emptyList(),
                            dinner = emptyList(),
                            snacks = emptyList(),
                            consumedCalories = 0
                        )}
                    } else {
                        updateState { it.copy(
                            breakfast = foodData.breakfast,
                            lunch = foodData.lunch,
                            dinner = foodData.dinner,
                            snacks = foodData.snacks,
                            consumedCalories = foodData.consumedCalories
                        )}
                    }
                }
        }
    }

    private data class FoodData(
        val breakfast: List<Food>,
        val lunch: List<Food>,
        val dinner: List<Food>,
        val snacks: List<Food>,
        val consumedCalories: Int
    )

    private fun LoggedFood.toFood(): Food {
        return Food(
            id = this.foodId,
            name = this.name,
            calories = this.calories,
            carbs = this.carbs,
            fat = this.fat,
            protein = this.protein,
            isMeal = false
        )
    }

    private fun foodForUser(
        mealType: MealType,
        userId: String
    ): Flow<List<LoggedFood>> {
        return userPreferencesRepository
            .getFoodsForMeal(mealType)
            .map { foods ->
                foods.filter { it.userId == userId }
            }
    }
}