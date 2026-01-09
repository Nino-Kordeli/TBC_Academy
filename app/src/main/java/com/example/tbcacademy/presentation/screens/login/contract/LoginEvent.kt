package com.example.tbcacademy.presentation.screens.login.contract

sealed class LoginEvent {
    data class EmailChanged(val email: String) : LoginEvent()
    data class PasswordChanged(val password: String) : LoginEvent()
    data class RememberMeToggled(val checked: Boolean) : LoginEvent()
    data class Submit(val email: String, val password: String) : LoginEvent()
    object NavigateToRegister : LoginEvent()
}
