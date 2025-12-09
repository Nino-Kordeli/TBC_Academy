package com.example.tbcacademy.presentation.auth

import androidx.lifecycle.viewModelScope
import com.example.tbcacademy.common.BaseViewModel
import com.example.tbcacademy.domain.usecase.LoginUseCase
import com.example.tbcacademy.domain.usecase.ObserveSessionUseCase
import com.example.tbcacademy.domain.usecase.RegisterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase,
    private val loginUseCase: LoginUseCase,
    private val observeSessionUseCase: ObserveSessionUseCase
) : BaseViewModel<AuthState, AuthEvent, AuthSideEffect>(initialState = AuthState()) {
    override fun onEvent(event: AuthEvent) {
        when (event) {
            AuthEvent.CheckSession -> observeSession()
            is AuthEvent.Login -> login(event.email, event.password)
            is AuthEvent.Register -> register(event.email, event.password, event.repeat)
        }
    }

    private fun register(email: String, password: String, repeat: String) {
        if (password != repeat){
            emitSideEffect(AuthSideEffect.ShowError("Passwords do not match"))
            return
        }
    }

    private fun login(email: String, password: String) {

    }

    private fun observeSession() {
        viewModelScope.launch {
            observeSessionUseCase().collectLatest { logged ->
                updateState { it.copy(isLoggedIn = logged) }
                if (logged) emitSideEffect(AuthSideEffect.NavigateToMain)
            }
        }
    }
}