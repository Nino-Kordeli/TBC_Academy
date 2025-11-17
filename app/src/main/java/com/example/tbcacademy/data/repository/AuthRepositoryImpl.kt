package com.example.tbcacademy.data.repository

import com.example.tbcacademy.data.dto.AuthRequestDto
import com.example.tbcacademy.data.remote.AuthApi
import com.example.tbcacademy.domain.model.AuthRequest
import com.example.tbcacademy.domain.model.LoginResponse
import com.example.tbcacademy.domain.model.RegisterResponse
import com.example.tbcacademy.domain.model.Result
import com.example.tbcacademy.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AuthRepositoryImpl(private val api: AuthApi) : AuthRepository {

    override suspend fun login(request: AuthRequest): Flow<Result<LoginResponse>> = flow {
        emit(Result.Loading)
        try {
            val resp = api.login(AuthRequestDto(request.email, request.password))
            if (resp.isSuccessful) {
                val token = resp.body()?.token.orEmpty()
                emit(Result.Success(LoginResponse(token)))
            } else {
                emit(Result.Error(Exception("${resp.code()} ${resp.message()}")))
            }
        } catch (e: Exception) {
            emit(Result.Error(e))
        }
    }

    override suspend fun register(request: AuthRequest): Flow<Result<RegisterResponse>> = flow {
        emit(Result.Loading)
        try {
            val resp = api.register(AuthRequestDto(request.email, request.password))

            if (resp.isSuccessful && resp.body() != null) {
                val body = resp.body()!!
                emit(Result.Success(RegisterResponse(body.id, body.token)))
            } else {
                val errorBody = resp.errorBody()?.string()
                val errorMsg = when {
                    errorBody != null -> "API Error: $errorBody"
                    resp.code() == 401 -> "Unauthorized"
                    else -> "Error ${resp.code()}: ${resp.message()}"
                }
                emit(Result.Error(Exception(errorMsg)))
            }
        } catch (e: Exception) {
            emit(Result.Error(Exception("Network error: ${e.message}")))
        }
    }
}