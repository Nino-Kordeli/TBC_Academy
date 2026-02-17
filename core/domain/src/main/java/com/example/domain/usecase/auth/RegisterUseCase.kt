package com.example.domain.usecase.auth

import com.example.domain.repository.auth.AuthRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String) {
        repository.register(email, password, rememberMe = true  )
    }
}