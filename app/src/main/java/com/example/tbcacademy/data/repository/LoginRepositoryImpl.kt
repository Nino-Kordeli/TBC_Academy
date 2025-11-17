package com.example.tbcacademy.data.repository

import com.example.tbcacademy.data.dto.AuthRequestDto
import com.example.tbcacademy.data.remote.AuthApi
import com.example.tbcacademy.domain.model.AuthRequest
import com.example.tbcacademy.domain.model.LoginResponse
import com.example.tbcacademy.domain.model.Result
import com.example.tbcacademy.domain.repository.LoginRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LoginRepositoryImpl(private val api: AuthApi) : LoginRepository {
    override suspend fun login(request: AuthRequest): Flow<Result<LoginResponse>> = flow {
        emit(Result.Loading)
        try {
            val resp = api.login(AuthRequestDto(request.email, request.password))
            if (resp.isSuccessful) {
                emit(Result.Success(LoginResponse(token = resp.body()?.token.orEmpty())))
            } else {
                emit(Result.Error(Exception("Login failed: ${resp.code()} ${resp.message()}")))
            }
        } catch (e: Exception) {
            emit(Result.Error(e))
        }
    }
}