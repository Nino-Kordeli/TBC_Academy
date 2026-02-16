package com.example.impl.screens.diary.vm

import androidx.compose.ui.graphics.Path.Companion.combine
import androidx.compose.ui.text.style.TextDecoration.Companion.combine
import androidx.lifecycle.viewModelScope
import com.example.domain.model.food.Food
import com.example.domain.model.food.LoggedFood
import com.example.domain.model.food.MealType
import com.example.impl.screens.diary.contract.DiaryEvent
import com.example.impl.screens.diary.contract.DiaryState
import com.example.ui.base.BaseViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

class DiaryViewModel @Inject constructor(
    private val userPreferences: UserPreferencesManager
) : BaseViewModel<DiaryState, DiaryEvent, Nothing>(DiaryState()) {

    init {
        checkAndReset()
        loadSavedFood()
    }

    private fun checkAndReset() {
        viewModelScope.launch {
            userPreferences.checkAndResetDailyData()
        }
    }

    private fun loadSavedFood() {
        viewModelScope.launch {
            combine(
                userPreferences.getFoodsForMeal(MealType.BREAKFAST),
                userPreferences.getFoodsForMeal(MealType.LUNCH),
                userPreferences.getFoodsForMeal(MealType.DINNER),
                userPreferences.getFoodsForMeal(MealType.SNACKS),
                userPreferences.getGoalCalories()
            ) { breakfast, lunch, dinner, snacks, goalCalories ->
                DiaryState(
                    breakfast = breakfast.map { it.toFood() },
                    lunch = lunch.map { it.toFood() },
                    dinner = dinner.map { it.toFood() },
                    snacks = snacks.map { it.toFood() },
                    goalCalories = goalCalories
                )
            }.collect { newState ->
                updateState { newState }
            }
        }
    }

    override fun onEvent(event: DiaryEvent) {
        when (event) {
            is DiaryEvent.AddFood -> {
                val loggedFood = event.food.toLoggedFood()

                viewModelScope.launch {
                    val currentFoods = when (event.mealType) {
                        MealType.BREAKFAST -> state.value.breakfast
                        MealType.LUNCH -> state.value.lunch
                        MealType.DINNER -> state.value.dinner
                        MealType.SNACKS -> state.value.snacks
                    }
                    val updateFoods = currentFoods +event.food
                    val updatedLoggedFoods = updateFoods.map { it.toLoggedFood() }
                    userPreferences.saveFoodsForMeal(event.mealType, updatedLoggedFoods)

                    updateState { current ->
                        when (event.mealType) {
                            MealType.BREAKFAST -> current.copy(breakfast = updateFoods)
                            MealType.LUNCH -> current.copy(lunch = updateFoods)
                            MealType.DINNER -> current.copy(dinner = updateFoods)
                            MealType.SNACKS -> current.copy(snacks = updateFoods)
                        }
                    }

                }
            }
        }
    }
}
private fun Food.toLoggedFood() = LoggedFood(
    id = UUID.randomUUID().toString(),
    foodId = id,
    name = name,
    amountGrams = 100,
    calories = calories,
    carbs = carbs,
    fat = fat,
    protein = protein,
    timestamp = System.currentTimeMillis()
)

private fun LoggedFood.toFood() = Food(
    id = foodId,
    name = name,
    calories = calories,
    carbs = carbs,
    fat = fat,
    protein = protein,
    isMeal = false
)