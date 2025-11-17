package com.example.tbcacademy.domain.usecase

import com.example.tbcacademy.data.repository.AuthRepositoryImpl
import com.example.tbcacademy.domain.model.RegisterResponse
import com.example.tbcacademy.domain.model.Result
import kotlinx.coroutines.flow.Flow

class RegisterUseCase(private val registerRepository: AuthRepositoryImpl) {
    suspend operator fun invoke(email: String, password: String): Flow<Result<RegisterResponse>> {
        return registerRepository.register(com.example.tbcacademy.domain.model.AuthRequest(email, password))
    }
}