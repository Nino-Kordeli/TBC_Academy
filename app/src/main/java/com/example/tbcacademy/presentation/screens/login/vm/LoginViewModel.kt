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
                        _events.emit(LoginEvent.ShowError(result.exception.message ?: "Login failed"))
                    }
                    else -> {}
                }
            }
        }
    }

    fun isLoggedIn(): Boolean = SessionManager.getToken(loginUseCase.context) != null
    fun isRememberMe(): Boolean = SessionManager.isRememberMe(loginUseCase.context)
}