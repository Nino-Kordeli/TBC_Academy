package com.example.impl.screens.dashboard.contract

import com.example.impl.screens.dashboard.model.CaloriesUiModel

data class DashboardUiState(
    val caloriesData: CaloriesUiModel = CaloriesUiModel(
        goal = 0,
        food = 0,
        exercise = 0
    ),
    val steps: Int = 0
)

sealed class DashboardEvent {
    data object GetGoalCalories : DashboardEvent()
}