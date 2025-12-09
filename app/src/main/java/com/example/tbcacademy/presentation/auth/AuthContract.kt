package com.example.tbcacademy.presentation.auth

sealed class AuthEvent {
    data class Register(val email: String, val password: String, val repeat: String) : AuthEvent()
    data class Login(val email: String, val password: String) : AuthEvent()
    object CheckSession : AuthEvent()
}

data class AuthState(
    val isLoading: Boolean = false,
    val isLoggedIn: Boolean = false,
    val error: String? = null
)

sealed interface AuthSideEffect {
    data class ShowError(val message: String) : AuthSideEffect
    data class ShowSuccess(val message: String) : AuthSideEffect
    object NavigateToMain : AuthSideEffect
}