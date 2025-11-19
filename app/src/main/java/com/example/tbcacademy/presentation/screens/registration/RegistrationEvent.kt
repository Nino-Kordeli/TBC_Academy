package com.example.tbcacademy.presentation.screens.registration

sealed class RegistrationEvent {
    data class RegisterClicked(val email: String, val password: String) : RegistrationEvent()
}