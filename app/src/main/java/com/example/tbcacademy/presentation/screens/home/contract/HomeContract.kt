package com.example.tbcacademy.presentation.screens.home.contract

data class HomeState(
    val isLoading: Boolean = false
)

sealed interface HomeEvent {
    object Refresh : HomeEvent
}

sealed interface HomeSideEffect {
    data class ShowError(val message: String) : HomeSideEffect
}