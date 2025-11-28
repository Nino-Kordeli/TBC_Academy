package com.example.tbcacademy.domain.repository

import com.example.tbcacademy.domain.model.Credentials
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun saveCredentials(firstName: String, lastName: String, email: String)
    fun getCredentials(): Flow<Credentials>
}