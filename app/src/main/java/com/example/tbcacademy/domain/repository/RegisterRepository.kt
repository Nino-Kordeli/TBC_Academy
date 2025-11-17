package com.example.tbcacademy.domain.repository

import com.example.tbcacademy.domain.model.AuthRequest
import com.example.tbcacademy.domain.model.RegisterResponse
import com.example.tbcacademy.domain.model.Result
import kotlinx.coroutines.flow.Flow

interface RegisterRepository {
    suspend fun register(request: AuthRequest): Flow<Result<RegisterResponse>>
}