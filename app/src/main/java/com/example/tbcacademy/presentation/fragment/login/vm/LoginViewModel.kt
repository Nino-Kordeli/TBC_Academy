package com.example.tbcacademy.presentation.fragment.login.vm

import androidx.lifecycle.viewModelScope
import com.example.tbcacademy.common.BaseViewModel
import com.example.tbcacademy.data.common.Resource
import com.example.tbcacademy.data.datastore.UserPreferences
import com.example.tbcacademy.domain.model.ValidationResult
import com.example.tbcacademy.domain.usecase.LoginUseCase
import com.example.tbcacademy.domain.usecase.ValidateLoginUseCase
import com.example.tbcacademy.presentation.fragment.login.contract.LoginEvent
import com.example.tbcacademy.presentation.fragment.login.contract.LoginSideEffect
import com.example.tbcacademy.presentation.fragment.login.contract.LoginState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val validateLoginUseCase: ValidateLoginUseCase,
    private val userPreferences: UserPreferences
) : BaseViewModel<LoginState, LoginEvent, LoginSideEffect>(LoginState()) {

    override fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.EmailChanged ->
                updateState { it.copy(email = event.value) }

            is LoginEvent.PasswordChanged ->
                updateState { it.copy(password = event.value) }

            is LoginEvent.RememberMeChanged ->
                updateState { it.copy(rememberMe = event.value) }

            LoginEvent.Submit -> login()
        }
    }

    private fun login() = viewModelScope.launch {
        val email = state.value.email
        val password = state.value.password

        when (val validation = validateLoginUseCase(email, password)) {
            is ValidationResult.Error -> {
                updateState { it.copy(error = validation.message, loading = false) }
                emitSideEffect(LoginSideEffect.ShowSnackBar(validation.message))
                return@launch
            }
            ValidationResult.Success -> Unit
        }

        loginUseCase(email, password).collect { result ->
            when (result) {

                is Resource.Loading -> {
                    updateState { it.copy(loading = true, error = null) }
                }

                is Resource.Error -> {
                    updateState { it.copy(loading = false, error = result.message) }
                    emitSideEffect(LoginSideEffect.ShowSnackBar(result.message))
                }

                is Resource.Success -> {
                    updateState { it.copy(loading = false, error = null) }

                    if (state.value.rememberMe) {
                        userPreferences.saveEmail(email)
                    }

                    emitSideEffect(LoginSideEffect.NavigateToHome)
                }
            }
        }
    }
}
