package com.example.tbcacademy.presentation.screens.login.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbcacademy.domain.model.Result
import com.example.tbcacademy.domain.usecase.LoginUseCase
import com.example.tbcacademy.utils.SessionManager
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

sealed class LoginEvent {
    data class ShowError(val message: String) : LoginEvent()
    object NavigateToHome : LoginEvent()
}

class LoginViewModel(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _events = MutableSharedFlow<LoginEvent>()
    val events: SharedFlow<LoginEvent> = _events

    fun login(email: String, password: String, remember: Boolean) {
        viewModelScope.launch {
            when {
                email.isEmpty() && password.isEmpty() -> {
                    _events.emit(LoginEvent.ShowError("Please enter email and password"))
                    return@launch
                }
                email.isEmpty() -> {
                    _events.emit(LoginEvent.ShowError("Please enter your email"))
                    return@launch
                }
                !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                    _events.emit(LoginEvent.ShowError("Please enter a valid email"))
                    return@launch
                }
                password.isEmpty() -> {
                    _events.emit(LoginEvent.ShowError("Please enter your password"))
                    return@launch
                }
                password.length < 6 -> {
                    _events.emit(LoginEvent.ShowError("Password must be at least 6 characters"))
                    return@launch
                }
            }

            loginUseCase(email, password, remember).collect { result ->
                when (result) {
                    is Result.Success -> {
                        val token = result.data.token
                        SessionManager.saveAuth(
                            context = loginUseCase.context,
                            token = token,
                            remember = remember,
                            email = email
                        )
                        _events.emit(LoginEvent.NavigateToHome)
                    }
                    is Result.Error -> {
                        val message = when {
                            result.exception.message?.contains("400") == true ->
                                "Invalid email or password"
                            result.exception.message?.contains("404") == true ->
                                "Account not found"
                            result.exception.message?.contains("401") == true ->
                                "Invalid credentials"
                            else -> result.exception.message ?: "Login failed"
                        }
                        _events.emit(LoginEvent.ShowError(message))
                    }
                    is Result.Loading -> {
                    }
                }
            }
        }
    }
}