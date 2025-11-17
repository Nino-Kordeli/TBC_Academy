package com.example.tbcacademy.domain.repository

import com.example.tbcacademy.domain.model.AuthRequest
import com.example.tbcacademy.domain.model.LoginResponse
import com.example.tbcacademy.domain.model.RegisterResponse
import com.example.tbcacademy.domain.model.Result
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun login(request: AuthRequest): Flow<Result<LoginResponse>>
    suspend fun register(request: AuthRequest): Flow<Result<RegisterResponse>>
}