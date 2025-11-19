package com.example.tbcacademy.presentation.screens.login

data class LoginState(
    val email: String = "",
    val password: String = "",
    val rememberMe: Boolean = false,
    val isLoading: Boolean = false
)
