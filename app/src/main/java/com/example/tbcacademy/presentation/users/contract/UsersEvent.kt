package com.example.tbcacademy.presentation.users.contract

import androidx.annotation.StringRes
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
    data class ShowError(val message: String? = null, @StringRes val messageResId: Int? = null) : UsersSideEffects
    data class ShowSuccess(@StringRes val messageResId: Int) : UsersSideEffects
}

