package com.example.impl.screens.register.contract

data class RegisterState(
    val email: String = "",
    val password: String = "",
    val repeatPassword: String = "",
    val isLoading: Boolean = false,
    val name: String = ""
)

sealed interface RegisterEvent {
    data class EmailChanged(val value: String) : RegisterEvent
    data class PasswordChanged(val value: String) : RegisterEvent
    data class RepeatPasswordChanged(val value: String) : RegisterEvent
    data object RegisterClicked : RegisterEvent
    data object LoginClicked : RegisterEvent
}

sealed interface RegisterSideEffect {
    data object NavigateToLogin : RegisterSideEffect
    data object NavigateToHome : RegisterSideEffect
    data class ShowError(val message: String) : RegisterSideEffect
}
