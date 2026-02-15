package com.example.impl.screens.login.vm

import android.util.Patterns
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.LoginUseCase
import com.example.impl.screens.login.contract.LoginEvent
import com.example.impl.screens.login.contract.LoginSideEffect
import com.example.impl.screens.login.contract.LoginState
import com.example.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel
@Inject constructor(
    private val loginUseCase: LoginUseCase
) : BaseViewModel<LoginState, LoginEvent, LoginSideEffect>(initialState = LoginState()) {

    override fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.EmailChanged ->
                updateState { it.copy(email = event.value) }

            is LoginEvent.PasswordChanged ->
                updateState { it.copy(password = event.value) }

            LoginEvent.LoginCLicked -> login()

            LoginEvent.RegisterClicked -> emitSideEffect(LoginSideEffect.NavigateToRegister)
        }
    }


    private fun login() {
        val currentState = state.value

        if (currentState.email.isBlank()) {
            emitSideEffect(LoginSideEffect.ShowError("Email is required"))
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(currentState.email).matches()) {
            emitSideEffect(LoginSideEffect.ShowError("Invalid email format"))
            return
        }

        if (currentState.password.isBlank()) {
            emitSideEffect(LoginSideEffect.ShowError("Password is required"))
            return
        }

        viewModelScope.launch {
            updateState { it.copy(isLoading = true) }

            runCatching {
                loginUseCase(currentState.email, currentState.password)
            }.onSuccess {
                emitSideEffect(LoginSideEffect.NavigateToHome)
            }.onFailure {
                emitSideEffect(
                    LoginSideEffect.ShowError(
                        it.message ?: "Login failed"
                    )
                )
            }

            updateState { it.copy(isLoading = false) }
        }
    }
}