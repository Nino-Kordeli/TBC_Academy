package com.example.tbcacademy.presentation.screens.register.model

sealed class RegisterUiState {
    object Idle : RegisterUiState()
    object Loading : RegisterUiState()
    data class Success(val token: String) : RegisterUiState()
    data class Error(val message: String) : RegisterUiState()
}
