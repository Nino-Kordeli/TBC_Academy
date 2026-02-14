package com.example.tbcacademy.presentation.screens.register.contract

data class RegisterState(
    val email: String = "",
    val password: String = "",
    val repeatPassword: String = "",
    val isLoading: Boolean = false
)

sealed interface RegisterEvent {
    data class EmailChanged(val value: String) : RegisterEvent
    data class PasswordChanged(val value: String) : RegisterEvent
    data class RepeatPasswordChanged(val value: String) : RegisterEvent
    object RegisterClicked : RegisterEvent
    object LoginClicked : RegisterEvent
}

sealed interface RegisterSideEffect {
    object NavigateToLogin : RegisterSideEffect
    object NavigateToHome : RegisterSideEffect
    data class ShowError(val message: String) : RegisterSideEffect
}