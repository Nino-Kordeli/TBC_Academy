package com.example.tbcacademy.presentation.screens.login.vm

import androidx.lifecycle.viewModelScope
import com.example.tbcacademy.common.BaseViewModel
import com.example.tbcacademy.domain.model.Result
import com.example.tbcacademy.domain.repository.SessionRepository
import com.example.tbcacademy.domain.usecase.LoginUseCase
import com.example.tbcacademy.presentation.screens.login.LoginEffect
import com.example.tbcacademy.presentation.screens.login.LoginEvent
import com.example.tbcacademy.presentation.screens.login.LoginState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val sessionRepository: SessionRepository
) : BaseViewModel<LoginState, LoginEvent, LoginEffect>(LoginState()) {

    override fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.EmailChanged ->
                setState { copy(email = event.email) }

            is LoginEvent.PasswordChanged ->
                setState { copy(password = event.password) }

            is LoginEvent.RememberMeToggled ->
                setState { copy(rememberMe = event.checked) }

            is LoginEvent.Submit ->
                login(event.email, event.password)

            LoginEvent.NavigateToRegister -> {
                viewModelScope.launch {
                    postEffect(LoginEffect.NavigateToRegister)
                }
            }
        }
    }

    private fun login(email: String, password: String) {
        viewModelScope.launch {
            loginUseCase(email, password).collect { result ->
                when (result) {
                    is Result.Loading -> {
                        setState { copy(isLoading = true) }
                    }

                    is Result.Success -> {
                        setState { copy(isLoading = false) }
                        sessionRepository.saveEmail(email)
                        postEffect(LoginEffect.NavigateToHome)
                    }

                    is Result.Error -> {
                        setState { copy(isLoading = false) }
                        postEffect(
                            LoginEffect.ShowError(
                                result.exception.message ?: "Unknown error"
                            )
                        )
                    }
                }
            }
        }
    }
}