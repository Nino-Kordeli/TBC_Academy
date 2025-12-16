package com.example.tbcacademy.presentation.register.contract

sealed interface RegisterEvent {
    data class EmailChanged(val value: String) : RegisterEvent
    data class PasswordChanged(val value: String) : RegisterEvent
    data class ConfirmPasswordChanged(val value: String) : RegisterEvent
    object Submit : RegisterEvent
}

data class RegisterState(
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val loading: Boolean = false,
    val error: String? = null
)

sealed interface RegisterSideEffect {
    object NavigateToHome : RegisterSideEffect
    data class ShowToast(val message: String) : RegisterSideEffect
}