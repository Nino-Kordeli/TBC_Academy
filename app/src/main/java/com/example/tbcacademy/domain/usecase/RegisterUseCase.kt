package com.example.tbcacademy.domain.usecase

import com.example.tbcacademy.domain.model.AuthRequest
import com.example.tbcacademy.domain.model.RegisterResponse
import com.example.tbcacademy.domain.model.Result
import com.example.tbcacademy.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): Result<RegisterResponse> =
        repository.register(AuthRequest(email, password))
}