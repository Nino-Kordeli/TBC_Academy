package com.example.impl.screens.dashboard.contract

import com.example.impl.screens.dashboard.model.CaloriesUiModel

data class DashboardUiState(
    val caloriesData: CaloriesUiModel = CaloriesUiModel(
        goal = 2000,
        food = 0,
        exercise = 0
    )
)

sealed class DashboardSideEffect {
    data object DashboardToCalories : DashboardSideEffect()
}

sealed class DashboardEvent {
    data object ProfileClicked : DashboardEvent()
    data object GetGoalCalories : DashboardEvent()
}