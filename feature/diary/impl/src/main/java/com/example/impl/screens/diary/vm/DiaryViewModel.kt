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
import kotlinx.coroutines.flow.combine
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

    private fun loadSavedFood() {
        viewModelScope.launch {
            combine(
                userPreferencesRepository.getFoodsForMeal(MealType.BREAKFAST),
                userPreferencesRepository.getFoodsForMeal(MealType.LUNCH),
                userPreferencesRepository.getFoodsForMeal(MealType.DINNER),
                userPreferencesRepository.getFoodsForMeal(MealType.SNACKS)
            ) { breakfast, lunch, dinner, snacks ->

                // Convert LoggedFood to Food for UI
                val breakfastFoods = breakfast.map { it.toFood() }
                val lunchFoods = lunch.map { it.toFood() }
                val dinnerFoods = dinner.map { it.toFood() }
                val snacksFoods = snacks.map { it.toFood() }

                // Calculate total consumed calories
                val totalConsumed = breakfast.sumOf { it.calories } +
                        lunch.sumOf { it.calories } +
                        dinner.sumOf { it.calories } +
                        snacks.sumOf { it.calories }

                DiaryState(
                    breakfast = breakfastFoods,
                    lunch = lunchFoods,
                    dinner = dinnerFoods,
                    snacks = snacksFoods,
                    goalCalories = state.value.goalCalories,
                    consumedCalories = totalConsumed,
                    exerciseCalories = 0 // TODO: Add exercise tracking
                )
            }.collect { newState ->
                updateState { newState }
            }
        }
    }

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
}