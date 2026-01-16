package com.example.tbcacademy.presentation.presentation.register.vm

import com.example.tbcacademy.presentation.presentation.common.BaseViewModel
import com.example.tbcacademy.presentation.presentation.register.contract.RegisterEvent
import com.example.tbcacademy.presentation.presentation.register.contract.RegisterSideEffect
import com.example.tbcacademy.presentation.presentation.register.contract.RegisterUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor() :
    BaseViewModel<RegisterUiState, RegisterEvent, RegisterSideEffect>(RegisterUiState()) {

    override fun onEvent(event: RegisterEvent) {
        when (event) {
            RegisterEvent.BackClicked -> emitSideEffect(RegisterSideEffect.RegisterToWelcome)
            is RegisterEvent.EmailChanged -> updateState { it.copy(email = event.email) }
            is RegisterEvent.PasswordChanged -> updateState { it.copy(password = event.password) }
            is RegisterEvent.UsernameChanged -> updateState { it.copy(username = event.username) }
            RegisterEvent.RegisterClicked -> handleNextStep()
        }
    }

    private fun handleNextStep() {
        val currentState = state.value
        when (currentState.step) {
            1 -> {
                if (isEmailValid(currentState.email) && isPasswordValid(currentState.password)) {
                    updateState { it.copy(step = 2) }
                } else {
                    emitSideEffect(RegisterSideEffect.ShowError("Invalid credentials"))
                }
            }
            2 -> {
                if (currentState.username.isNotBlank()) {
                    emitSideEffect(RegisterSideEffect.RegisterToLogin)
                } else {
                    emitSideEffect(RegisterSideEffect.ShowError("Username cannot be empty"))
                }
            }
        }
    }

    private fun isEmailValid(email: String) = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    private fun isPasswordValid(password: String) = password.length >= 6
}
