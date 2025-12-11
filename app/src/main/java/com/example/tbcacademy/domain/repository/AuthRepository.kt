package com.example.tbcacademy.domain.repository

import com.example.tbcacademy.data.common.Resource
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun register(email: String, password: String): Flow<Resource<Boolean>>
    fun login(email: String, password: String): Flow<Resource<Boolean>>
    fun isUserLoggedIn(): Flow<Boolean>
    suspend fun signOut()
}
