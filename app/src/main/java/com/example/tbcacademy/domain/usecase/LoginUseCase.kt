package com.example.tbcacademy.domain.usecase

import android.content.Context
import com.example.tbcacademy.data.repository.AuthRepositoryImpl
import com.example.tbcacademy.domain.model.AuthRequest
import com.example.tbcacademy.domain.model.LoginResponse
import com.example.tbcacademy.domain.model.Result
import com.example.tbcacademy.domain.repository.SessionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach

class LoginUseCase(
    private val loginRepository: AuthRepositoryImpl,
    private val sessionRepository: SessionRepository,
    val context: Context
) {
    suspend operator fun invoke(
        email: String,
        password: String,
        rememberMe: Boolean
    ): Flow<Result<LoginResponse>> {
        return loginRepository.login(AuthRequest(email, password))
            .onEach { result ->
                if (result is Result.Success && rememberMe) {
                    sessionRepository.saveToken(result.data.token)
                    sessionRepository.saveEmail(email)
                    sessionRepository.saveRememberMe(rememberMe)
                }
            }
    }
}