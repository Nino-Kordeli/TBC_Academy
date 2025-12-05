package com.example.tbcacademy.presentation.users.vm

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.viewModelScope
import com.example.tbcacademy.R
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
    private val observeUsersUseCase: ObserveUsersUseCase,
    private val application: Application
) : BaseViewModel<UsersState, UsersEvent, UsersSideEffects>(initialState = UsersState()) {

    init {
        observeUsers()
    }

    override fun onEvent(event: UsersEvent) {
        when (event) {
            UsersEvent.FetchUsers -> fetchUsers()
            UsersEvent.ObserveUsers -> observeUsers()
        }
    }

    private fun fetchUsers() {
        val isOnline = isNetworkAvailable()
        updateState { it.copy(isOnline = isOnline) }

        if (!isOnline) {
            emitSideEffect(UsersSideEffects.ShowError(messageResId = R.string.offline_showing_cached))
            return
        }

        viewModelScope.launch {
            fetchUsersUseCase().collectLatest { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        updateState { it.copy(isLoading = resource.loading) }
                    }

                    is Resource.Success -> {
                        emitSideEffect(UsersSideEffects.ShowSuccess(R.string.users_loaded_successfully))
                    }

                    is Resource.Error -> {
                        updateState { it.copy(isLoading = false) }
                        emitSideEffect(
                            UsersSideEffects.ShowError(
                                resource.errorMessage.ifEmpty { null },
                                messageResId = R.string.unknown_error
                            )
                        )
                    }
                }
            }
        }
    }

    private fun observeUsers() {
        viewModelScope.launch {
            observeUsersUseCase().collectLatest { usersList ->
                updateState { it.copy(users = usersList) }
            }
        }
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager =
            application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
}