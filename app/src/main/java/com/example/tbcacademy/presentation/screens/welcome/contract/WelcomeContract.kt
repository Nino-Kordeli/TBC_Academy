package com.example.tbcacademy.presentation.screens.welcome.contract

data class WelcomeState(
    val defaultState: Boolean = true
)

sealed interface WelcomeEvent {
    object LoginClicked : WelcomeEvent
    object RegisterClicked : WelcomeEvent
}

sealed class WelcomeSideEffect {
    data class NavigateToLogin(val destination: String) : WelcomeSideEffect()
    data class NavigateToRegister(val destination: String) : WelcomeSideEffect()
}