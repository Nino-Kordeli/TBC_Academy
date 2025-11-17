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