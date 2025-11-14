package com.example.tbcacademy.data.repository

import com.example.tbcacademy.data.dto.AuthRequestDto
import com.example.tbcacademy.data.remote.RetrofitInstance
import com.example.tbcacademy.domain.model.AuthRequest
import com.example.tbcacademy.domain.model.LoginResponse
import com.example.tbcacademy.domain.model.RegisterResponse
import com.example.tbcacademy.domain.repository.AuthRepository
import com.example.tbcacademy.domain.repository.TokenRepository
import retrofit2.Response

class AuthRepositoryImpl(
    private val tokenRepository: TokenRepository
) : AuthRepository {

    private val api = RetrofitInstance.create(tokenRepository)

    override suspend fun register(request: AuthRequest): Response<RegisterResponse> {
        val response = api.register(
            AuthRequestDto(request.email, request.password)
        )

        val body = response.body()

        return if (response.isSuccessful && body != null) {
            Response.success(RegisterResponse(id = body.id, token = body.token))
        } else {
            Response.error(response.errorBody()!!, response.raw())
        }
    }

    override suspend fun login(request: AuthRequest): Response<LoginResponse> {
        val response = api.login(
            AuthRequestDto(request.email, request.password)
        )

        val body = response.body()

        return if (response.isSuccessful && body != null) {
            Response.success(LoginResponse(token = body.token))
        } else {
            Response.error(response.errorBody()!!, response.raw())
        }
    }

    override suspend fun saveToken(token: String) {
        tokenRepository.saveToken(token)
    }

    override suspend fun saveUsername(username: String) {
        tokenRepository.saveUsername(username)
    }

    override suspend fun saveEmail(email: String) {
        tokenRepository.saveEmail(email)
    }
}
