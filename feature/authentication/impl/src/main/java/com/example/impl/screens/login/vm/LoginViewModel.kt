package com.example.impl.screens.login.vm

import android.util.Patterns
import androidx.lifecycle.viewModelScope
import com.example.domain.repository.UserSessionRepository
import com.example.domain.usecase.auth.CheckAutoLoginUseCase
import com.example.domain.usecase.auth.LoginUseCase
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
    private val loginUseCase: LoginUseCase,
    private val checkAutoLoginUseCase: CheckAutoLoginUseCase,
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
                viewModelScope.launch {
                    userSessionRepository.setRememberMe(event.value)
                }
            }

            LoginEvent.LoginCLicked -> login()

            LoginEvent.RegisterClicked -> emitSideEffect(LoginSideEffect.NavigateToRegister)
        }
    }

    fun checkAutoLogin() {
        viewModelScope.launch {
            val remember = userSessionRepository.getRememberMe()
            val savedEmail = userSessionRepository.getSavedEmail()
            val savedPassword = userSessionRepository.getSavedPassword()

            updateState { it.copy(
                rememberMe = remember,
                email = savedEmail ?: "",
                password = savedPassword ?: ""
            ) }

            if (remember && !savedEmail.isNullOrBlank() && !savedPassword.isNullOrBlank()) {
                login(auto = true)
            }
        }
    }

    private fun login(auto: Boolean = false) {
        val currentState = state.value

        if (!auto) { // only validate if user pressed login
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
        }

        viewModelScope.launch {
            updateState { it.copy(isLoading = true) }

            runCatching {
                loginUseCase(
                    email = currentState.email,
                    password = currentState.password,
                    rememberMe = currentState.rememberMe
                )
            }.onSuccess {
                emitSideEffect(LoginSideEffect.NavigateToHome)
            }.onFailure {
                if (!auto) {
                    emitSideEffect(
                        LoginSideEffect.ShowError(
                            it.message ?: "Login failed"
                        )
                    )
                }
            }

            updateState { it.copy(isLoading = false) }
        }
    }
}
