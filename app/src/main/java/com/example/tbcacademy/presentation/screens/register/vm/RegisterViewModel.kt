package com.example.tbcacademy.presentation.screens.register.vm

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbcacademy.domain.model.AuthRequest
import com.example.tbcacademy.domain.model.RegisterResponse
import com.example.tbcacademy.domain.repository.AuthRepository
import com.example.tbcacademy.presentation.screens.register.model.RegisterUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Response

class RegisterViewModel(
    private val repository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<RegisterUiState>(RegisterUiState.Loading)
    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()


    fun register(email: String, password: String, userName: String) {
        val validationError = validateInputs(email, password, userName)
        if (validationError != null) {
            _uiState.value = RegisterUiState.Error(validationError)
            return
        }

        viewModelScope.launch {
            _uiState.value = RegisterUiState.Loading

            try {
                val response = repository.register(AuthRequest(email.trim(), password))
                handleResponse(response, userName, email.trim())
            } catch (t: Throwable) {
                _uiState.value = RegisterUiState.Error("An unexpected error occurred")
            }
        }
    }

    private fun validateInputs(email: String, password: String, userName: String): String? {
        return when {
            email.isBlank() || password.isBlank() || userName.isBlank() -> "Please fill out all fields"
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> "Invalid email"
            password.length < MIN_PASSWORD_LENGTH -> "Password must be at least $MIN_PASSWORD_LENGTH characters"
            else -> null
        }
    }

    private fun handleResponse(
        response: Response<RegisterResponse>,
        userName: String,
        email: String
    ) {
        if (response.isSuccessful) {
            val body = response.body()
            val token = body?.token

            if (!token.isNullOrBlank()) {
                viewModelScope.launch {
                    repository.saveToken(token)
                    repository.saveUsername(userName)
                    repository.saveEmail(email)
                }
                _uiState.value = RegisterUiState.Success(token)
            } else {
                _uiState.value = RegisterUiState.Error("No token received")
            }
        } else {
            _uiState.value = RegisterUiState.Error(response.errorBody()?.string() ?: "Server error")
        }
    }

    fun resetState() {
        _uiState.value = RegisterUiState.Idle
    }

    companion object {
        private const val MIN_PASSWORD_LENGTH = 6
    }
}
