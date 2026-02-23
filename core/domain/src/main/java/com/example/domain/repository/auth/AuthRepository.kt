package com.example.domain.repository.auth

import com.example.common.resource.Resource
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun login(email: String, password: String): Flow<Resource<String>>
    suspend fun register(email: String, password: String, rememberMe: Boolean):String
    suspend fun logout()
    fun isLoggedIn(): Boolean
}