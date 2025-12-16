package com.example.tbcacademy.domain.usecase.auth

import com.example.tbcacademy.domain.model.ValidationResult
import javax.inject.Inject

class ValidateLoginUseCase @Inject constructor() {
    operator fun invoke(email: String, password: String): ValidationResult {
        if (email.isBlank()) {
            return ValidationResult.Error("Email is required")
        }
        if (!isEmailValid(email)) {
            return ValidationResult.Error("Invalid email format")
        }
        if (password.isBlank()) {
            return ValidationResult.Error("Password is required")
        }
        if (password.length < 6) {
            return ValidationResult.Error("Password too short")
        }
        return ValidationResult.Success
    }

    private fun isEmailValid(email: String): Boolean {
        return Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$").matches(email)
    }
}
