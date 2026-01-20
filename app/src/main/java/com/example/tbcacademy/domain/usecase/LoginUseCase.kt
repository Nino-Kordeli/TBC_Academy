package com.example.tbcacademy.domain.usecase

import android.util.Patterns
import com.example.tbcacademy.domain.repository.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {

    suspend operator fun invoke(email: String, password: String) {
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            throw IllegalArgumentException("Invalid email")
        }

        if (password.length < 6) {
            throw IllegalArgumentException("Password too short")
        }

        authRepository.login(email, password)
    }
}
