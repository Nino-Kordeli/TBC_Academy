package com.example.impl.screens.welcome.contract

data class WelcomeState(
    val defaultState: Boolean = true
)

sealed interface WelcomeEvent {
    object LoginClicked : WelcomeEvent
    object RegisterClicked : WelcomeEvent
}

sealed class WelcomeSideEffect {
    data object NavigateToLogin : WelcomeSideEffect()
    data object NavigateToRegister : WelcomeSideEffect()
}