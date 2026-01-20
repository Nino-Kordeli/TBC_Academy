package com.example.myapplication.presentation.welcome.contract

data class WelcomeUiState(
    val default: Boolean = true
)

sealed class WelcomeSideEffect {
    data object NavigateToLogin : WelcomeSideEffect()
    data object NavigateToRegister : WelcomeSideEffect()
}

sealed class WelcomeEvent {
    data object LoginClicked : WelcomeEvent()
    data object RegisterClicked : WelcomeEvent()
}