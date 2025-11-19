package com.example.tbcacademy.domain.usecase

import com.example.tbcacademy.domain.model.AuthRequest
import com.example.tbcacademy.domain.model.LoginResponse
import com.example.tbcacademy.domain.model.Result
import com.example.tbcacademy.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    operator fun invoke(email: String, password: String): Flow<Result<LoginResponse>> =
        repository.login(AuthRequest(email, password))
}