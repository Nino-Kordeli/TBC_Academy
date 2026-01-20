package com.example.tbcacademy.presentation.login.vm

import androidx.lifecycle.viewModelScope
import com.example.tbcacademy.domain.usecase.LoginUseCase
import com.example.tbcacademy.presentation.common.BaseViewModel
import com.example.tbcacademy.presentation.login.contract.LoginEvent
import com.example.tbcacademy.presentation.login.contract.LoginSideEffect
import com.example.tbcacademy.presentation.login.contract.LoginUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : BaseViewModel<LoginUiState, LoginEvent, LoginSideEffect>(LoginUiState()) {

    override fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.EmailChanged ->
                updateState { it.copy(email = event.email) }

            is LoginEvent.PasswordChanged ->
                updateState { it.copy(password = event.password) }

            LoginEvent.LoginClicked ->
                login()

            LoginEvent.BackClicked ->
                emitSideEffect(LoginSideEffect.ReturnToRegister)
        }
    }

    private fun login() {
        val current = state.value

        viewModelScope.launch {
            try {
                loginUseCase(
                    email = current.email,
                    password = current.password
                )
                emitSideEffect(LoginSideEffect.LoginToProfile)
            } catch (e: Exception) {
                emitSideEffect(
                    LoginSideEffect.ShowError(e.message ?: "Login failed")
                )
            }
        }
    }
}