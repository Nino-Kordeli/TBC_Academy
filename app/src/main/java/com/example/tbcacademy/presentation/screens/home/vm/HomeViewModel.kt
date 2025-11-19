package com.example.tbcacademy.presentation.screens.home.vm

import androidx.lifecycle.viewModelScope
import com.example.tbcacademy.common.BaseViewModel
import com.example.tbcacademy.domain.model.Result
import com.example.tbcacademy.domain.model.User
import com.example.tbcacademy.domain.usecase.GetUsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getUsersUseCase: GetUsersUseCase
) : BaseViewModel<HomeState, HomeEvent, HomeEffect>(HomeState()) {

    init {
        onEvent(HomeEvent.LoadUsers)
    }

    override fun onEvent(event: HomeEvent) {
        when (event) {
            HomeEvent.LoadUsers -> loadUsers()
            HomeEvent.Retry -> loadUsers()
        }
    }

    private fun loadUsers() = viewModelScope.launch {
        setState { copy(isLoading = true) }

        val result = getUsersUseCase()
        when (result) {
            is Result.Success -> setState { copy(users = result.data) }
            is Result.Error -> postEffect(HomeEffect.ShowError(result.exception.message ?: "Failed"))
            else -> Unit
        }

        setState { copy(isLoading = false) }
    }
}

data class HomeState(val isLoading: Boolean = true, val users: List<User> = emptyList())
sealed class HomeEvent {
    object LoadUsers : HomeEvent()
    object Retry : HomeEvent()
}

sealed class HomeEffect {
    data class ShowError(val message: String) : HomeEffect()
}