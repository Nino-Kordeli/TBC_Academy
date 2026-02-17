package com.example.impl.screens.register.vm

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
) : BaseViewModel<RegisterState, RegisterEvent, RegisterSideEffect>(
    initialState = RegisterState()
) {

    override fun onEvent(event: RegisterEvent) {
        when (event) {
            is RegisterEvent.EmailChanged ->
                updateState { it.copy(email = event.value) }

            RegisterEvent.LoginClicked ->
                emitSideEffect(RegisterSideEffect.NavigateToLogin)

            is RegisterEvent.PasswordChanged ->
                updateState { it.copy(password = event.value) }

            RegisterEvent.RegisterClicked -> register()

            is RegisterEvent.RepeatPasswordChanged ->
                updateState { it.copy(repeatPassword = event.value) }
        }
    }

    private fun register() {
        val state = state.value

        if (state.password != state.repeatPassword) {
            emitSideEffect(
                RegisterSideEffect.ShowError("Passwords do not match")
            )
            return
        }
        viewModelScope.launch {
            updateState { it.copy(isLoading = true) }

            runCatching {
                registerUseCase(state.email, state.password)
            }.onSuccess {
                emitSideEffect(RegisterSideEffect.NavigateToHome)
            }.onFailure { throwable ->
//                    val message = when (throwable) {
//                        is FirebaseAuthUserCollisionException ->
//                            "Email already in use"
//                        else ->
//                            throwable.message ?: "Registration failed"
//                    }
//                    emitSideEffect(RegisterSideEffect.ShowError(message))

            }
            updateState { it.copy(isLoading = false) }
        }
    }
}