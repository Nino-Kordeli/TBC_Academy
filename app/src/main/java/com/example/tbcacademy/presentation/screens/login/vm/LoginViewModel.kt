package com.example.tbcacademy.presentation.screens.login.vm

import androidx.lifecycle.viewModelScope
import com.example.tbcacademy.domain.model.Result
import com.example.tbcacademy.domain.repository.SessionRepository
import com.example.tbcacademy.domain.usecase.LoginUseCase
import com.example.tbcacademy.presentation.common.BaseViewModel
import com.example.tbcacademy.presentation.screens.login.contract.LoginEffect
import com.example.tbcacademy.presentation.screens.login.contract.LoginEvent
import com.example.tbcacademy.presentation.screens.login.contract.LoginState
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
                updateState { it.copy(email = event.email) }

            is LoginEvent.PasswordChanged ->
                updateState { it.copy(password = event.password) }

            is LoginEvent.RememberMeToggled ->
                updateState { it.copy(rememberMe = event.checked) }

            is LoginEvent.Submit ->
                login(event.email, event.password)

            LoginEvent.NavigateToRegister -> {
                emitSideEffect(LoginEffect.NavigateToRegister)
            }
        }
    }

    private fun login(email: String, password: String) {
        viewModelScope.launch {
            loginUseCase(email, password).collect { result ->
                when (result) {
                    is Result.Loading -> {
                        updateState { it.copy(isLoading = true) }
                    }

                    is Result.Success -> {
                        updateState { it.copy(isLoading = false) }

                        sessionRepository.saveToken(result.data.token)
                        sessionRepository.saveEmail(email)
                        sessionRepository.saveRememberMe(state.value.rememberMe)

                        emitSideEffect(LoginEffect.NavigateToHome)
                    }

                    is Result.Error -> {
                        updateState { it.copy(isLoading = false) }
                        emitSideEffect(
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
