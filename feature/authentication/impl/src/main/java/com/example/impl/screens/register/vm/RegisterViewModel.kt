package com.example.impl.screens.register.vm

import android.util.Patterns
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.auth.RegisterUseCase
import com.example.impl.screens.register.contract.RegisterEvent
import com.example.impl.screens.register.contract.RegisterSideEffect
import com.example.impl.screens.register.contract.RegisterState
import com.example.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase
) : BaseViewModel<RegisterState, RegisterEvent, RegisterSideEffect>(RegisterState()) {

    override fun onEvent(event: RegisterEvent) {
        when (event) {
            is RegisterEvent.EmailChanged ->
                updateState { it.copy(email = event.value) }

            RegisterEvent.LoginClicked ->
                emitSideEffect(RegisterSideEffect.NavigateToLogin)

            is RegisterEvent.PasswordChanged ->
                updateState { it.copy(password = event.value) }

            RegisterEvent.RegisterClicked -> {
                if (!state.value.isLoading) register()
            }

            is RegisterEvent.RepeatPasswordChanged ->
                updateState { it.copy(repeatPassword = event.value) }
        }
    }

    private fun register() {
        val currentState = state.value

        if (currentState.email.isBlank()) {
            emitSideEffect(RegisterSideEffect.ShowError("Email is required"))
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(currentState.email).matches()) {
            emitSideEffect(RegisterSideEffect.ShowError("Invalid email format"))
            return
        }

        if (currentState.password.isBlank()) {
            emitSideEffect(RegisterSideEffect.ShowError("Password is required"))
            return
        }

        if (currentState.password != currentState.repeatPassword) {
            emitSideEffect(RegisterSideEffect.ShowError("Passwords do not match"))
            return
        }

        viewModelScope.launch {
            try {
                updateState { it.copy(isLoading = true) }

                registerUseCase(
                    email = currentState.email,
                    password = currentState.password,
                    rememberMe = true,
                    name = currentState.name
                )

                emitSideEffect(RegisterSideEffect.NavigateToHome)
            } catch (e: Exception) {
                emitSideEffect(
                    RegisterSideEffect.ShowError(
                        e.message ?: "Registration failed"
                    )
                )
            } finally {
                updateState { it.copy(isLoading = false) }
            }
        }
    }
}