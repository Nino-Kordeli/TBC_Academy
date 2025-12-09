package com.example.tbcacademy.domain.repository

import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun register(email: String, password: String): Result<Unit>
    suspend fun login(email: String, password: String): Result<Unit>
    suspend fun signOut()
    fun isUserLoggedIn(): Flow<Boolean>
}