package com.example.tbcacademy.presentation.screens.registration.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbcacademy.domain.usecase.RegisterUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

sealed class RegisterEvent {
    data class NavigateBack(val email: String, val password: String) : RegisterEvent()
    data class ShowError(val message: String) : RegisterEvent()
}

class RegistrationViewModel(
    private val registerUseCase: RegisterUseCase
) : ViewModel() {

    private val _events = MutableSharedFlow<RegisterEvent>()
    val events: SharedFlow<RegisterEvent> = _events

    fun register(email: String, password: String) {
        viewModelScope.launch {
            when {
                email.isEmpty() -> {
                    _events.emit(RegisterEvent.ShowError("Please enter email"))
                    return@launch
                }
                password.isEmpty() -> {
                    _events.emit(RegisterEvent.ShowError("Please enter password"))
                    return@launch
                }
                password.length < 6 -> {
                    _events.emit(RegisterEvent.ShowError("Password must be at least 6 characters"))
                    return@launch
                }
            }

            registerUseCase(email, password).collect { result ->
                when (result) {
                    is com.example.tbcacademy.domain.model.Result.Success -> {
                        _events.emit(RegisterEvent.NavigateBack(email, password))
                    }
                    is com.example.tbcacademy.domain.model.Result.Error -> {
                        _events.emit(RegisterEvent.ShowError(result.exception.message ?: "Registration failed"))
                    }
                    else -> {}
                }
            }
        }
    }
}