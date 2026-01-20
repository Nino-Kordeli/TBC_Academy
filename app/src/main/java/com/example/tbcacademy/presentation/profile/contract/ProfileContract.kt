package com.example.tbcacademy.presentation.profile.contract

data class ProfileUiState(
    val default: Boolean = false
)

sealed class ProfileSideEffect {
    data object ProfileToLogin : ProfileSideEffect()
}

sealed class ProfileEvent {
    data object LogoutClicked : ProfileEvent()
}