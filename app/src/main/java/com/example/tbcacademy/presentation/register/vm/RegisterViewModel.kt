package com.example.tbcacademy.presentation.register.vm

import androidx.lifecycle.viewModelScope
import com.example.tbcacademy.domain.usecase.RegisterUseCase
import com.example.tbcacademy.presentation.common.BaseViewModel
import com.example.tbcacademy.presentation.register.contract.RegisterEvent
import com.example.tbcacademy.presentation.register.contract.RegisterSideEffect
import com.example.tbcacademy.presentation.register.contract.RegisterUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase
) : BaseViewModel<RegisterUiState, RegisterEvent, RegisterSideEffect>(RegisterUiState()) {

    override fun onEvent(event: RegisterEvent) {
        when (event) {
            RegisterEvent.BackClicked ->
                emitSideEffect(RegisterSideEffect.RegisterToWelcome)

            is RegisterEvent.EmailChanged ->
                updateState { it.copy(email = event.email) }

            is RegisterEvent.PasswordChanged ->
                updateState { it.copy(password = event.password) }

            is RegisterEvent.UsernameChanged ->
                updateState { it.copy(username = event.username) }

            RegisterEvent.RegisterClicked ->
                handleNextStep()
        }
    }

    private fun handleNextStep() {
        val current = state.value

        when (current.step) {
            1 -> updateState { it.copy(step = 2) }

            2 -> register(current)
        }
    }

    private fun register(state: RegisterUiState) {
        viewModelScope.launch {
            try {
                registerUseCase(
                    email = state.email,
                    password = state.password,
                    username = state.username
                )
                emitSideEffect(RegisterSideEffect.RegisterToLogin)
            } catch (e: Exception) {
                emitSideEffect(
                    RegisterSideEffect.ShowError(
                        e.message ?: "Registration failed"
                    )
                )
            }
        }
    }
}