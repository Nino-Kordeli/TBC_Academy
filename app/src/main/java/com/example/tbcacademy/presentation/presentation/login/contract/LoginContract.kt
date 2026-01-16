package com.example.tbcacademy.presentation.presentation.login.contract

data class LoginUiState(
    val email: String = "",
    val password: String = ""
)

sealed class LoginSideEffect {
    data object LoginToProfile : LoginSideEffect()
    data object ReturnToRegister : LoginSideEffect()
    data class ShowError(val message: String) : LoginSideEffect()
}

sealed class LoginEvent {
    data object BackClicked: LoginEvent()
    data object LoginClicked: LoginEvent()
    data class EmailChanged(val email: String) : LoginEvent()
    data class PasswordChanged(val password: String) : LoginEvent()
}