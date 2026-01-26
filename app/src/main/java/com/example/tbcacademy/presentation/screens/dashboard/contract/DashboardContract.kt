package com.example.tbcacademy.presentation.screens.dashboard.contract

data class DashboardUiState(
    val default: Boolean
)

sealed class DashboardSideEffect {
    data object DashboardToCalories : DashboardSideEffect()
}

sealed class DashboardEvent {
    data object ProfileClicked : DashboardEvent()
}