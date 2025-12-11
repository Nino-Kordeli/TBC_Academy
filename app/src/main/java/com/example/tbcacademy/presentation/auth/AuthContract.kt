package com.example.tbcacademy.presentation.auth

data class AuthState(
    val email: String = "",
    val password: String = "",
    val loading: Boolean = false,
    val isSuccess: Boolean = false,
    val errorMessage: String? = null
)

sealed class AuthEvent {
    data class OnEmailChanged(val value: String) : AuthEvent()
    data class OnPasswordChanged(val value: String) : AuthEvent()
    object OnLogin : AuthEvent()
    object OnRegister : AuthEvent()
}

sealed class AuthSideEffect {
    object NavigateHome : AuthSideEffect()
}
