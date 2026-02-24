package com.example.impl.contract

data class ProfileState(
    val user: String = "",
    val email: String = "",
)

sealed interface ProfileEvent {
    object LogoutCLicked : ProfileEvent
}

sealed interface ProfileSideEffect {
    object NavigateToLogin : ProfileSideEffect
}