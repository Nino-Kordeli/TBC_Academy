package com.example.impl.screens.welcome.contract

sealed interface WelcomeEvent {
    object LoginClicked : WelcomeEvent
    object RegisterClicked : WelcomeEvent
}

sealed class WelcomeSideEffect {
    data object NavigateToLogin : WelcomeSideEffect()
    data object NavigateToRegister : WelcomeSideEffect()
}