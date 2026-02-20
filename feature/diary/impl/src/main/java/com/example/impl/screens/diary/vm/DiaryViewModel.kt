package com.example.impl.screens.diary.vm

import androidx.lifecycle.viewModelScope
import com.example.domain.model.food.Food
import com.example.domain.model.food.LoggedFood
import com.example.model.MealType
import com.example.domain.repository.UserPreferencesRepository
import com.example.impl.screens.diary.contract.DiaryEvent
import com.example.impl.screens.diary.contract.DiaryState
import com.example.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class DiaryViewModel @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository
) : BaseViewModel<DiaryState, DiaryEvent, Nothing>(DiaryState()) {

    init {
        observeMeals()
    }

    private fun observeMeals() {
        viewModelScope.launch {
            combine(
                userPreferencesRepository.getFoodsForMeal(MealType.BREAKFAST),
                userPreferencesRepository.getFoodsForMeal(MealType.LUNCH),
                userPreferencesRepository.getFoodsForMeal(MealType.DINNER),
                userPreferencesRepository.getFoodsForMeal(MealType.SNACKS),
                userPreferencesRepository.getGoalCalories()
            ) { breakfast, lunch, dinner, snacks, goalCalories ->
                DiaryState(
                    breakfast = breakfast.map(::toFood),
                    lunch = lunch.map(::toFood),
                    dinner = dinner.map(::toFood),
                    snacks = snacks.map(::toFood),
                    goalCalories = goalCalories
                )
            }.collect { updateState { it } }
        }
    }

    override fun onEvent(event: DiaryEvent) {
        when (event) {
            is DiaryEvent.AddFood -> viewModelScope.launch {
                userPreferencesRepository.addFood(
                    event.mealType,
                    toLoggedFood(event.food)
                )
            }
        }
    }

    private fun toLoggedFood(food: Food) = LoggedFood(
        id = UUID.randomUUID().toString(),
        foodId = food.id,
        name = food.name,
        amountGrams = 100,
        calories = food.calories,
        carbs = food.carbs,
        fat = food.fat,
        protein = food.protein,
        timestamp = System.currentTimeMillis()
    )

    private fun toFood(logged: LoggedFood) = Food(
        id = logged.foodId,
        name = logged.name,
        calories = logged.calories,
        carbs = logged.carbs,
        fat = logged.fat,
        protein = logged.protein,
        isMeal = false
    )
}