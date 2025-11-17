package com.example.tbcacademy.domain.usecase

import com.example.tbcacademy.domain.model.RegisterResponse
import com.example.tbcacademy.domain.model.Result
import com.example.tbcacademy.domain.repository.RegisterRepository
import kotlinx.coroutines.flow.Flow

class RegisterUseCase(private val registerRepository: RegisterRepository) {
    suspend operator fun invoke(email: String, password: String): Flow<Result<RegisterResponse>> {
        return registerRepository.register(com.example.tbcacademy.domain.model.AuthRequest(email, password))
    }
}