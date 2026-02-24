package com.example.impl.screens.dashboard.vm

import androidx.lifecycle.viewModelScope
import com.example.domain.repository.StepCounterRepository
import com.example.domain.repository.UserPreferencesRepository
import com.example.impl.screens.dashboard.contract.DashboardEvent
import com.example.impl.screens.dashboard.contract.DashboardSideEffect
import com.example.impl.screens.dashboard.contract.DashboardUiState
import com.example.impl.screens.dashboard.model.CaloriesUiModel
import com.example.model.MealType
import com.example.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
@OptIn(ExperimentalCoroutinesApi::class)
class DashboardViewModel @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository,
    private val stepCounterRepository: StepCounterRepository
) : BaseViewModel<DashboardUiState, DashboardEvent, DashboardSideEffect>(
    initialState = DashboardUiState()
) {
    init {
        loadCaloriesData()
        collectSteps()
    }

    override fun onEvent(event: DashboardEvent) {
        when (event) {
            DashboardEvent.ProfileClicked -> {}
            DashboardEvent.GetGoalCalories -> loadCaloriesData()
            DashboardEvent.StartStepCounting -> {}
        }
    }

    private fun collectSteps() {
        viewModelScope.launch {
            stepCounterRepository.steps.collect { steps ->
                updateState { it.copy(steps = steps) }
            }
        }
    }

    private fun loadCaloriesData() {
        viewModelScope.launch {
            userPreferencesRepository.getCurrentUserId()
                .flatMapLatest { userId ->
                    combine(
                        userPreferencesRepository.getGoalCalories(),
                        userPreferencesRepository.getExerciseCalories(),
                        combine(
                            userPreferencesRepository.getFoodsForMeal(MealType.BREAKFAST),
                            userPreferencesRepository.getFoodsForMeal(MealType.LUNCH),
                            userPreferencesRepository.getFoodsForMeal(MealType.DINNER),
                            userPreferencesRepository.getFoodsForMeal(MealType.SNACKS)
                        ) { breakfast, lunch, dinner, snacks ->
                            breakfast.filter { it.userId == userId }.sumOf { it.calories } +
                                    lunch.filter { it.userId == userId }.sumOf { it.calories } +
                                    dinner.filter { it.userId == userId }.sumOf { it.calories } +
                                    snacks.filter { it.userId == userId }.sumOf { it.calories }
                        }
                    ) { goalCalories, exerciseCalories, totalConsumed ->
                        CaloriesUiModel(
                            goal = goalCalories,
                            food = totalConsumed,
                            exercise = exerciseCalories
                        )
                    }
                }
                .collect { caloriesData ->
                    updateState { it.copy(caloriesData = caloriesData) }
                }
        }
    }
}