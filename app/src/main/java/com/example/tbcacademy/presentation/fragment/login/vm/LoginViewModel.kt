package com.example.tbcacademy.presentation.fragment.login.vm

import androidx.lifecycle.viewModelScope
import com.example.tbcacademy.common.BaseViewModel
import com.example.tbcacademy.data.common.Resource
import com.example.tbcacademy.domain.usecase.LoginUseCase
import com.example.tbcacademy.presentation.fragment.login.contract.LoginEvent
import com.example.tbcacademy.presentation.fragment.login.contract.LoginSideEffect
import com.example.tbcacademy.presentation.fragment.login.contract.LoginState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : BaseViewModel<LoginState, LoginEvent, LoginSideEffect>(LoginState()) {

    override fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.EmailChanged -> updateState { it.copy(email = event.value) }
            is LoginEvent.PasswordChanged -> updateState { it.copy(password = event.value) }
            LoginEvent.Submit -> login()
        }
    }

    private fun login() = viewModelScope.launch {
        val email = state.value.email
        val password = state.value.password

        loginUseCase(email, password).collect { resource ->
            when (resource) {
                is Resource.Loading -> {
                    updateState { it.copy(loading = true, error = null) }
                }

                is Resource.Success -> {
                    updateState { it.copy(loading = false, error = null) }
                    emitSideEffect(LoginSideEffect.NavigateToHome)
                }

                is Resource.Error -> {
                    updateState { it.copy(loading = false, error = resource.message) }
                    emitSideEffect(LoginSideEffect.ShowToast(resource.message))
                }
            }
        }
    }
}