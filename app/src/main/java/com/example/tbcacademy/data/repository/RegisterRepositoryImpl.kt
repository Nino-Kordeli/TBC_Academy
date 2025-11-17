package com.example.tbcacademy.data.repository

import com.example.tbcacademy.data.dto.AuthRequestDto
import com.example.tbcacademy.data.remote.AuthApi
import com.example.tbcacademy.domain.model.AuthRequest
import com.example.tbcacademy.domain.model.RegisterResponse
import com.example.tbcacademy.domain.model.Result
import com.example.tbcacademy.domain.repository.RegisterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RegisterRepositoryImpl(private val api: AuthApi) : RegisterRepository {
    override suspend fun register(request: AuthRequest): Flow<Result<RegisterResponse>> = flow {
        emit(Result.Loading)
        try {
            val resp = api.register(AuthRequestDto(request.email, request.password))
            if (resp.isSuccessful) emit(Result.Success(RegisterResponse(resp.body()?.id ?: -1, resp.body()?.token.orEmpty())))
            else emit(Result.Error(Exception("${resp.code()} ${resp.message()}")))
        } catch (e: Exception) { emit(Result.Error(e)) }
    }
}