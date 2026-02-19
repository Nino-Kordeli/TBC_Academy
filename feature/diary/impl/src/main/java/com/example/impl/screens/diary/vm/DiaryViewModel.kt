package com.example.impl.screens.diary.vm

import androidx.lifecycle.viewModelScope
import com.example.domain.model.food.Food
import com.example.domain.model.food.LoggedFood
import com.example.domain.model.food.MealType
import com.example.impl.screens.diary.contract.DiaryEvent
import com.example.impl.screens.diary.contract.DiaryState
import com.example.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class DiaryViewModel @Inject constructor(
//    private val userPreferencesRepository: UserPreferencesRepository
) : BaseViewModel<DiaryState, DiaryEvent, Nothing>(DiaryState()) {

//    init {
//        checkAndReset()
//        loadSavedFood()
//    }
//
//    private fun checkAndReset() {
//        viewModelScope.launch {
//            userPreferencesRepository.checkAndResetDailyData()
//        }
//    }
//
//    private fun loadSavedFood() {
//        viewModelScope.launch {
//            combine(
//                userPreferencesRepository.getFoodsForMeal(MealType.BREAKFAST),
//                userPreferencesRepository.getFoodsForMeal(MealType.LUNCH),
//                userPreferencesRepository.getFoodsForMeal(MealType.DINNER),
//                userPreferencesRepository.getFoodsForMeal(MealType.SNACKS),
//                userPreferencesRepository.getGoalCalories()
//            ) { breakfast, lunch, dinner, snacks, goalCalories ->
//                DiaryState(
//                    breakfast = breakfast.map { toFood(it) },
//                    lunch = lunch.map { toFood(it) },
//                    dinner = dinner.map { toFood(it) },
//                    snacks = snacks.map { toFood(it) },
//                    goalCalories = goalCalories
//                )
//            }.collect { newState ->
//                updateState { newState }
//            }
//        }
//    }

    override fun onEvent(event: DiaryEvent) {
        when (event) {
            is DiaryEvent.AddFood -> {
                viewModelScope.launch {
                    val currentFoods = when (event.mealType) {
                        MealType.BREAKFAST -> state.value.breakfast
                        MealType.LUNCH -> state.value.lunch
                        MealType.DINNER -> state.value.dinner
                        MealType.SNACKS -> state.value.snacks
                    }

                    val updatedFoods = currentFoods + event.food
                    val updatedLoggedFoods = updatedFoods.map { toLoggedFood(it) }

//                    userPreferencesRepository.saveFoodsForMeal(event.mealType, updatedLoggedFoods)

                    updateState { current ->
                        when (event.mealType) {
                            MealType.BREAKFAST -> current.copy(breakfast = updatedFoods)
                            MealType.LUNCH -> current.copy(lunch = updatedFoods)
                            MealType.DINNER -> current.copy(dinner = updatedFoods)
                            MealType.SNACKS -> current.copy(snacks = updatedFoods)
                        }
                    }
                }
            }
        }
    }

    private fun toLoggedFood(food: Food): LoggedFood {
        return LoggedFood(
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
    }

    private fun toFood(loggedFood: LoggedFood): Food {
        return Food(
            id = loggedFood.foodId,
            name = loggedFood.name,
            calories = loggedFood.calories,
            carbs = loggedFood.carbs,
            fat = loggedFood.fat,
            protein = loggedFood.protein,
            isMeal = false
        )
    }
}