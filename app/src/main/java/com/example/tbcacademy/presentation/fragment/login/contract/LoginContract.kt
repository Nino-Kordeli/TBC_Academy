package com.example.tbcacademy.presentation.fragment.login.contract

sealed interface LoginEvent {
    data class EmailChanged(val value: String) : LoginEvent
    data class PasswordChanged(val value: String) : LoginEvent
    object Submit : LoginEvent
}

data class LoginState(
    val email: String = "",
    val password: String = "",
    val loading: Boolean = false,
    val error: String? = null
)

sealed interface LoginSideEffect {
    object NavigateToHome : LoginSideEffect
    data class ShowToast(val message: String) : LoginSideEffect
}
