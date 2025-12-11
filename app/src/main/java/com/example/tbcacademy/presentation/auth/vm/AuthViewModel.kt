package com.example.tbcacademy.presentation.auth.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbcacademy.data.common.Resource
import com.example.tbcacademy.domain.usecase.LoginUseCase
import com.example.tbcacademy.domain.usecase.RegisterUseCase
import com.example.tbcacademy.presentation.auth.AuthEvent
import com.example.tbcacademy.presentation.auth.AuthSideEffect
import com.example.tbcacademy.presentation.auth.AuthState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val registerUseCase: RegisterUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(AuthState())
    val state: StateFlow<AuthState> = _state

    private val _sideEffect = Channel<AuthSideEffect>()
    val sideEffect = _sideEffect.receiveAsFlow()

    fun onEvent(event: AuthEvent) {
        when (event) {
            is AuthEvent.OnEmailChanged ->
                _state.value = _state.value.copy(email = event.value)

            is AuthEvent.OnPasswordChanged ->
                _state.value = _state.value.copy(password = event.value)

            AuthEvent.OnLogin -> login()
            AuthEvent.OnRegister -> register()
        }
    }

    private fun login() = viewModelScope.launch {
        loginUseCase(_state.value.email, _state.value.password)
            .collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        _state.value = _state.value.copy(loading = true, errorMessage = null)
                    }

                    is Resource.Success -> {
                        _state.value = _state.value.copy(
                            loading = false,
                            isSuccess = true,
                            errorMessage = null
                        )
                        _sideEffect.send(AuthSideEffect.NavigateHome)
                    }

                    is Resource.Error -> {
                        _state.value = _state.value.copy(
                            loading = false,
                            isSuccess = false,
                            errorMessage = resource.message
                        )
                    }
                }
            }
    }

    private fun register() = viewModelScope.launch {
        registerUseCase(_state.value.email, _state.value.password)
            .collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        _state.value = _state.value.copy(loading = true, errorMessage = null)
                    }

                    is Resource.Success -> {
                        _state.value = _state.value.copy(
                            loading = false,
                            isSuccess = true,
                            errorMessage = null
                        )
                        _sideEffect.send(AuthSideEffect.NavigateHome)
                    }

                    is Resource.Error -> {
                        _state.value = _state.value.copy(
                            loading = false,
                            isSuccess = false,
                            errorMessage = resource.message
                        )
                    }
                }
            }
    }
}