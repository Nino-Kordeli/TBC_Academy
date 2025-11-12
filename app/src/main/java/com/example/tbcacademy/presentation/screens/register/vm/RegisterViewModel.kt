package com.example.tbcacademy.presentation.screens.register.vm

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbcacademy.data.dto.RegisterResponse
import com.example.tbcacademy.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException
import org.json.JSONObject

sealed class RegisterUiState {
    data object Idle : RegisterUiState()
    data object Loading : RegisterUiState()
    data class Success(val token: String) : RegisterUiState()
    data class Error(val message: String) : RegisterUiState()
}

class RegisterViewModel(
    private val repository: AuthRepository = AuthRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow<RegisterUiState>(RegisterUiState.Idle)
    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()

    private fun updateUiState(newState: RegisterUiState) {
        _uiState.value = newState
    }

    fun register(email: String, password: String, userName: String) {
        val validationError = validateInputs(email, password, userName)
        if (validationError != null) {
            updateUiState(RegisterUiState.Error(validationError))
            return
        }

        viewModelScope.launch {
            updateUiState(RegisterUiState.Loading)

            try {
                val response = repository.register(email.trim(), password)
                handleResponse(response)
            } catch (io: IOException) {
                updateUiState(RegisterUiState.Error("Network error"))
            } catch (t: Throwable) {
                updateUiState(RegisterUiState.Error("An unexpected error occurred"))
            }
        }
    }

    private fun validateInputs(email: String, password: String, userName: String): String? {
        return when {
            email.isBlank() || password.isBlank() || userName.isBlank() ->
                "Please fill out all the fields"

            !isValidEmail(email) ->
                "Please enter a valid email address"

            !email.equals(ALLOWED_EMAIL, ignoreCase = true) ->
                "Registration is not available for this email"

            password.length < MIN_PASSWORD_LENGTH ->
                "Password must be at least $MIN_PASSWORD_LENGTH characters"

            else -> null
        }
    }

    private fun handleResponse(response: Response<RegisterResponse>) {
        if (response.isSuccessful) {
            val body = response.body()
            val token = body?.token

            if (!token.isNullOrBlank()) {
                updateUiState(RegisterUiState.Success(token))
            } else {
                updateUiState(RegisterUiState.Error("Registration failed. No token received."))
            }
        } else {
            val errorMsg = parseErrorBody(response.errorBody()?.string())
            updateUiState(RegisterUiState.Error(errorMsg))
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun resetState() {
        updateUiState(RegisterUiState.Idle)
    }

    private fun parseErrorBody(body: String?): String {
        if (body.isNullOrBlank()) return "Server error. Please try again."

        return try {
            val json = JSONObject(body)
            json.optString("error", "An error occurred. Please try again.")
        } catch (e: Exception) {
            "An error occurred. Please try again."
        }
    }

    companion object {
        private const val ALLOWED_EMAIL = "eve.holt@reqres.in"
        private const val MIN_PASSWORD_LENGTH = 6
    }
}
