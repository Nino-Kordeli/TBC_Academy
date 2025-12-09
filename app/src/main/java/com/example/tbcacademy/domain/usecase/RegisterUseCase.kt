package com.example.tbcacademy.domain.usecase

import com.example.tbcacademy.domain.repository.AuthRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val repo: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String) =
        repo.register(email = email, password = password)
}