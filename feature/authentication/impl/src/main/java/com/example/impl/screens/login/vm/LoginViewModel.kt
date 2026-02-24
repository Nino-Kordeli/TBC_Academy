package com.example.impl.screens.login.vm

import android.util.Patterns
import androidx.lifecycle.viewModelScope
import com.example.common.resource.Resource
import com.example.domain.repository.user_session.UserSessionRepository
import com.example.domain.usecase.auth.LoginUseCase
import com.example.impl.screens.login.contract.LoginEvent
import com.example.impl.screens.login.contract.LoginSideEffect
import com.example.impl.screens.login.contract.LoginState
import com.example.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val userSessionRepository: UserSessionRepository
) : BaseViewModel<LoginState, LoginEvent, LoginSideEffect>(initialState = LoginState()) {

    override fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.EmailChanged ->
                updateState { it.copy(email = event.value) }

            is LoginEvent.PasswordChanged ->
                updateState { it.copy(password = event.value) }

            is LoginEvent.RememberMeChanged -> {
                updateState { it.copy(rememberMe = event.value) }
            }

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
            loginUseCase.invoke(currentState.email, currentState.password).collect { result ->
                when (result) {
                    is Resource.Error<*> -> {
                        emitSideEffect(
                            LoginSideEffect.ShowError(
                                result.errorMessage
                            )
                        )
                    }

                    is Resource.Loading<*> -> {
                        updateState { it.copy(isLoading = result.loading) }
                    }

                    is Resource.Success -> {
                        userSessionRepository.saveEmail(currentState.email)
                        if (currentState.rememberMe) {
                            userSessionRepository.savePassword(currentState.password)
                        } else {
                            userSessionRepository.savePassword("")
                        }
                        emitSideEffect(LoginSideEffect.NavigateToHome)
                    }
                }
            }
        }
    }
}
