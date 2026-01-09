package com.example.tbcacademy.presentation.screens.registration.contract

sealed class RegistrationEffect {
    data class NavigateBackWithCredentials(val email: String, val password: String) : RegistrationEffect()
    data class ShowError(val message: String) : RegistrationEffect()
}