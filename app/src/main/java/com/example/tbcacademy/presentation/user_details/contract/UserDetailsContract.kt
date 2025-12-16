package com.example.tbcacademy.presentation.user_details.contract

sealed interface UserDetailEvent {
    object Logout : UserDetailEvent
}

data class UserDetailState(
    val userEmail: String = "",
    val loading: Boolean = false
)

sealed interface UserDetailSideEffect {
    object NavigateToWelcome : UserDetailSideEffect
    data class ShowToast(val message: String) : UserDetailSideEffect
}