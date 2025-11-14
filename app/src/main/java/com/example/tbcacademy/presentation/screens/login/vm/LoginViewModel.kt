package com.example.tbcacademy.presentation.screens.login.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbcacademy.domain.model.AuthRequest
import com.example.tbcacademy.domain.model.LoginResponse
import com.example.tbcacademy.domain.repository.AuthRepository
import com.example.tbcacademy.domain.repository.TokenRepository
import com.example.tbcacademy.utils.validation.isValidEmail
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Response
import java.io.IOException

sealed class LoginUiState {
    data object Idle : LoginUiState()
    data object Loading : LoginUiState()
    data class Success(val token: String) : LoginUiState()
    data class Error(val message: String) : LoginUiState()
}

class LoginViewModel(
    private val repository: AuthRepository,
    private val tokenRepository: TokenRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<LoginUiState>(LoginUiState.Idle)
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun login(email: String, password: String) {
        val validationError = validateInputs(email, password)
        if (validationError != null) {
            _uiState.value = LoginUiState.Error(validationError)
            return
        }

        viewModelScope.launch {
            _uiState.value = LoginUiState.Loading
            try {
                val response = repository.login(AuthRequest(email.trim(), password))
                handleResponse(response)
            } catch (io: IOException) {
                _uiState.value = LoginUiState.Error("Network error")
            } catch (t: Throwable) {
                _uiState.value = LoginUiState.Error("Unexpected error: ${t.localizedMessage}")
            }
        }
    }

    private fun validateInputs(email: String, password: String): String? {
        return when {
            email.isBlank() || password.isBlank() -> "Please fill out all the fields"
            !isValidEmail(email) -> "Please enter a valid email address"
            else -> null
        }
    }

    private fun handleResponse(response: Response<LoginResponse>) {
        if (response.isSuccessful) {
            val token = response.body()?.token
            if (!token.isNullOrBlank()) {
                _uiState.value = LoginUiState.Success(token)
                viewModelScope.launch { tokenRepository.saveToken(token) }
            } else {
                _uiState.value = LoginUiState.Error("Login failed: No token received")
            }
        } else {
            val msg = parseErrorBody(response.errorBody()?.string())
            _uiState.value = LoginUiState.Error(msg)
        }
    }

    private fun parseErrorBody(body: String?): String {
        return if (body.isNullOrBlank()) "Server error" else try {
            val json = JSONObject(body)
            json.optString("error", "An error occurred")
        } catch (e: Exception) {
            "An error occurred"
        }
    }

    fun resetState() {
        _uiState.value = LoginUiState.Idle
    }
}
