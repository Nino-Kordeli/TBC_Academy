package com.example.impl.screens.login.contract

data class LoginState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false
)

sealed interface LoginEvent {
    data class EmailChanged(val value: String) : LoginEvent
    data class PasswordChanged(val value: String) : LoginEvent
    object LoginCLicked : LoginEvent
    object RegisterClicked : LoginEvent
}

sealed interface LoginSideEffect {
    object NavigateToHome : LoginSideEffect
    object NavigateToRegister : LoginSideEffect
    data class ShowError(val message: String) : LoginSideEffect
}