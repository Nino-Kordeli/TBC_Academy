package com.example.tbcacademy.presentation.users.contract

import com.example.tbcacademy.domain.model.User

data class UsersState(
    val users: List<User>? = null
)

sealed interface UsersEvent {
    data object FetchUsers : UsersEvent
}

sealed interface UsersSideEffects {

}