package com.example.tbcacademy.presentation.users.contract

import com.example.tbcacademy.domain.model.User

data class UsersState(
    val users: List<User>? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val isOnline: Boolean = true
)

sealed interface UsersEvent {
    data object FetchUsers : UsersEvent
    data object ObserveUsers : UsersEvent
}

sealed interface UsersSideEffects {
    data class ShowError(val message: String) : UsersSideEffects
    data class ShowSuccess(val message: String) : UsersSideEffects
}


