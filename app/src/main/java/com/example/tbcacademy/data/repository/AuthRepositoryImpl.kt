package com.example.tbcacademy.data.repository

import com.example.tbcacademy.data.dto.AuthRequestDto
import com.example.tbcacademy.data.remote.AuthApi
import com.example.tbcacademy.domain.model.AuthRequest
import com.example.tbcacademy.utils.safeApiCall
import com.example.tbcacademy.domain.model.LoginResponse
import com.example.tbcacademy.domain.model.RegisterResponse
import com.example.tbcacademy.domain.model.Result
import com.example.tbcacademy.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    @Named("auth") private val api: AuthApi
) : AuthRepository {

    override fun login(request: AuthRequest): Flow<Result<LoginResponse>> = flow {
        emit(Result.Loading)

        val result = safeApiCall {
            api.login(AuthRequestDto(request.email, request.password))
        }

        if (result is Result.Success) {
            emit(Result.Success(LoginResponse(result.data.token)))
        } else if (result is Result.Error) {
            emit(result)
        }
    }

    override suspend fun register(request: AuthRequest): Result<RegisterResponse> {
        val result = safeApiCall {
            api.register(AuthRequestDto(request.email, request.password))
        }

        return if (result is Result.Success) {
            Result.Success(RegisterResponse(result.data.id, result.data.token))
        } else {
            result as Result.Error
        }
    }
}