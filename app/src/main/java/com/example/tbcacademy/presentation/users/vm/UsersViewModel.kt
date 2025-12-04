package com.example.tbcacademy.presentation.users.vm

import androidx.lifecycle.viewModelScope
import androidx.room.util.copy
import com.example.tbcacademy.common.BaseViewModel
import com.example.tbcacademy.data.common.Resource
import com.example.tbcacademy.domain.usecase.FetchUsersUseCase
import com.example.tbcacademy.domain.usecase.ObserveUsersUseCase
import com.example.tbcacademy.presentation.users.contract.UsersEvent
import com.example.tbcacademy.presentation.users.contract.UsersSideEffects
import com.example.tbcacademy.presentation.users.contract.UsersState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(
    private val fetchUsersUseCase: FetchUsersUseCase,
    private val observeUsersUseCase: ObserveUsersUseCase
) : BaseViewModel<UsersState, UsersEvent, UsersSideEffects>(initialState = UsersState()) {

    override fun onEvent(event: UsersEvent) {
        when (event) {
            UsersEvent.FetchUsers -> fetchUsers()
            UsersEvent.ObserveUsers -> observeUsers()
        }
    }

    private fun fetchUsers() {
        viewModelScope.launch {
            fetchUsersUseCase().collectLatest { res ->
                when (res) {
                    is Resource.Loading -> {
                        /*updateState { it.copy(isLoading = res.loading) }*/
                    }

                    is Resource.Success -> {
                    }

                    is Resource.Error -> {
                        /*updateState { it.copy(error = res.errorMessage) }
                        emitSideEffect(
                            UsersSideEffect.ShowToast(
                                res.errorMessage ?: "Unknown error"
                            )
                        )*/
                    }
                }
            }
        }
    }

    private fun observeUsers() {
        viewModelScope.launch {
            observeUsersUseCase().collectLatest { usersList ->
                updateState { currentState ->
                    currentState.copy(users = usersList)
                }
            }
        }
    }
}