package com.example.tbcacademy.domain.repository

import com.example.tbcacademy.domain.model.AuthRequest
import com.example.tbcacademy.domain.model.LoginResponse
import com.example.tbcacademy.domain.model.RegisterResponse
import retrofit2.Response

interface AuthRepository {
    suspend fun register(request: AuthRequest): Response<RegisterResponse>
    suspend fun login(request: AuthRequest): Response<LoginResponse>

    suspend fun saveToken(token: String)
    suspend fun saveUsername(username: String)
    suspend fun saveEmail(email: String)
}