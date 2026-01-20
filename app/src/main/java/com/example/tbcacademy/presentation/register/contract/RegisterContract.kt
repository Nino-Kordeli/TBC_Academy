package com.example.tbcacademy.presentation.register.contract

data class RegisterUiState(
    val default: Boolean = true,
    val email: String = "",
    val password: String = "",
    val step:Int = 1,
    val username: String = ""
)

sealed class RegisterSideEffect {
    data object RegisterToLogin : RegisterSideEffect()
    data object RegisterToWelcome : RegisterSideEffect()
    data class ShowError(val message: String) : RegisterSideEffect()
}

sealed class RegisterEvent {
    data object RegisterClicked : RegisterEvent()
    data object BackClicked : RegisterEvent()
    data class EmailChanged(val email: String) : RegisterEvent()
    data class PasswordChanged(val password: String) : RegisterEvent()
    data class UsernameChanged(val username: String) : RegisterEvent()
}