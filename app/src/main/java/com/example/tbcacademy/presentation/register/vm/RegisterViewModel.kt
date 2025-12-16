package com.example.tbcacademy.presentation.register.vm

import androidx.lifecycle.viewModelScope
import com.example.tbcacademy.common.BaseViewModel
import com.example.tbcacademy.data.common.Resource
import com.example.tbcacademy.domain.model.ValidationResult
import com.example.tbcacademy.domain.usecase.auth.RegisterUseCase
import com.example.tbcacademy.domain.usecase.auth.ValidateRegistrationUseCase
import com.example.tbcacademy.presentation.register.contract.RegisterEvent
import com.example.tbcacademy.presentation.register.contract.RegisterSideEffect
import com.example.tbcacademy.presentation.register.contract.RegisterState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase,
    private val validateRegisterUseCase: ValidateRegistrationUseCase,
) : BaseViewModel<RegisterState, RegisterEvent, RegisterSideEffect>(RegisterState()) {

    override fun onEvent(event: RegisterEvent) {
        when (event) {
            is RegisterEvent.EmailChanged -> updateState { it.copy(email = event.value) }
            is RegisterEvent.PasswordChanged -> updateState { it.copy(password = event.value) }
            is RegisterEvent.ConfirmPasswordChanged -> updateState { it.copy(confirmPassword = event.value) }
            RegisterEvent.Submit -> register()
        }
    }

    private fun register() = viewModelScope.launch {
        val email = state.value.email
        val password = state.value.password
        val confirm = state.value.confirmPassword

        when (val validation = validateRegisterUseCase(email, password, confirm)) {
            is ValidationResult.Error -> {
                emitSideEffect(RegisterSideEffect.ShowToast(validation.message))
                return@launch
            }

            ValidationResult.Success -> Unit
        }

        registerUseCase(email, password).collect { result ->
            when (result) {
                is Resource.Success -> emitSideEffect(RegisterSideEffect.NavigateToHome)
                is Resource.Error -> emitSideEffect(RegisterSideEffect.ShowToast(result.errorMessage))
                is Resource.Loading -> updateState { it.copy(loading = true) }
            }
        }
    }
}
