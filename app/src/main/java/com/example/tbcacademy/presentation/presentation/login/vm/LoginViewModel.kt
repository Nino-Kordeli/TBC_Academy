package com.example.tbcacademy.presentation.presentation.login.vm

import android.util.Patterns
import com.example.tbcacademy.presentation.presentation.common.BaseViewModel
import com.example.tbcacademy.presentation.presentation.login.contract.LoginEvent
import com.example.tbcacademy.presentation.presentation.login.contract.LoginSideEffect
import com.example.tbcacademy.presentation.presentation.login.contract.LoginUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() :
    BaseViewModel<LoginUiState, LoginEvent, LoginSideEffect>(LoginUiState()) {

    override fun onEvent(event: LoginEvent) {
        when (event) {
            LoginEvent.BackClicked ->
                emitSideEffect(LoginSideEffect.ReturnToRegister)

            is LoginEvent.EmailChanged ->
                updateState { it.copy(email = event.email) }

            is LoginEvent.PasswordChanged ->
                updateState { it.copy(password = event.password) }

            LoginEvent.LoginClicked ->
                validateLogin()
        }
    }

    private fun validateLogin() {
        val current = state.value
        if (
            Patterns.EMAIL_ADDRESS.matcher(current.email).matches() &&
            current.password.length >= 6
        ) {
            emitSideEffect(LoginSideEffect.LoginToProfile)
        } else {
            emitSideEffect(LoginSideEffect.ShowError("Invalid Credentials"))
        }
    }
}