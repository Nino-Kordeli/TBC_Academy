package com.example.tbcacademy.domain.repository

import com.example.tbcacademy.domain.model.AuthRequest
import com.example.tbcacademy.domain.model.LoginResponse
import com.example.tbcacademy.domain.model.Result
import kotlinx.coroutines.flow.Flow

interface LoginRepository {
    suspend fun login(request: AuthRequest): Flow<Result<LoginResponse>>
}