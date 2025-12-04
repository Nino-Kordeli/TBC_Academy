package com.example.tbcacademy.presentation.users.vm

import android.util.Log.d
import androidx.lifecycle.viewModelScope
import com.example.tbcacademy.common.BaseViewModel
import com.example.tbcacademy.data.common.Resource
import com.example.tbcacademy.domain.usecase.FetchUsersUseCase
import com.example.tbcacademy.presentation.users.contract.UsersEvent
import com.example.tbcacademy.presentation.users.contract.UsersSideEffects
import com.example.tbcacademy.presentation.users.contract.UsersState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(
    private val fetchUsersUseCase: FetchUsersUseCase
) : BaseViewModel<UsersState, UsersEvent, UsersSideEffects>(initialState = UsersState()) {

    init {
        loadUsers()
    }

    override fun onEvent(event: UsersEvent) {
        when (event) {
            UsersEvent.FetchUsers -> loadUsers()
        }
    }

    private fun loadUsers() {
        viewModelScope.launch {
            fetchUsersUseCase().collectLatest { res ->
                when (res) {
                    is Resource.Loading -> {
                        /*updateState { it.copy(isLoading = res.loading) }*/
                    }

                    is Resource.Success -> {
                        d("messageasikdasjdh", res.data.toString())
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
}