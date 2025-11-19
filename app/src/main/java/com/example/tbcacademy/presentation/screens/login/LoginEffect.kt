package com.example.tbcacademy.presentation.screens.login

sealed class LoginEffect {
    object NavigateToHome : LoginEffect()
    data class ShowError(val message: String) : LoginEffect()
    object NavigateToRegister : LoginEffect()
}
