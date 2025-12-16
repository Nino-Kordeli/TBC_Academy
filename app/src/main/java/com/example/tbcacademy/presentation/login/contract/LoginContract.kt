package com.example.tbcacademy.presentation.login.contract

sealed interface LoginEvent {
    data class EmailChanged(val value: String) : LoginEvent
    data class PasswordChanged(val value: String) : LoginEvent
    data class RememberMeChanged(val value: Boolean) : LoginEvent
    object Submit : LoginEvent
}

data class LoginState(
    val email: String = "",
    val password: String = "",
    val rememberMe: Boolean = false,
    val loading: Boolean = false,
    val error: String? = null
)

sealed interface LoginSideEffect {
    object NavigateToHome : LoginSideEffect
    data class ShowSnackBar(val message: String) : LoginSideEffect
}
