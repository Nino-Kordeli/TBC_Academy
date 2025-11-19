package com.example.tbcacademy.presentation.screens.registration.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbcacademy.domain.usecase.RegisterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class RegisterEvent {
    data class RegisterSuccess(val email: String, val password: String) : RegisterEvent()
    data class ShowError(val message: String) : RegisterEvent()
}

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase
) : ViewModel() {

    private val _events = MutableSharedFlow<RegisterEvent>()
    val events: SharedFlow<RegisterEvent> = _events

    fun register(email: String, password: String, confirmPassword: String) {
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
                confirmPassword.isEmpty() -> {
                    _events.emit(RegisterEvent.ShowError("Please confirm password"))
                    return@launch
                }
                password != confirmPassword -> {
                    _events.emit(RegisterEvent.ShowError("Passwords do not match"))
                    return@launch
                }
                password.length < 6 -> {
                    _events.emit(RegisterEvent.ShowError("Password must be at least 6 characters"))
                    return@launch
                }
            }

            when (val result = registerUseCase(email, password)) {
                is com.example.tbcacademy.domain.model.Result.Success -> {
                    _events.emit(RegisterEvent.RegisterSuccess(email, password))
                }
                is com.example.tbcacademy.domain.model.Result.Error -> {
                    _events.emit(RegisterEvent.ShowError(result.exception.message ?: "Registration failed"))
                }
                else -> {}
            }
        }
    }
}