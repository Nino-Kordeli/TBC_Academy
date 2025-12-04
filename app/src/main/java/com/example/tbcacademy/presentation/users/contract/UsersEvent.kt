package com.example.tbcacademy.presentation.users.contract

import com.example.tbcacademy.domain.model.User

data class UsersState(
    val users: List<User>? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

sealed interface UsersEvent {
    data object FetchUsers : UsersEvent
    data object ObserveUsers: UsersEvent
}

sealed interface UsersSideEffects {

}