package com.example.domain.repository

import kotlinx.coroutines.flow.Flow

interface UserSessionRepository {
    suspend fun saveSession(token: String, rememberMe: Boolean)
    suspend fun getRememberMe(): Boolean
    suspend fun getToken(): String?
    suspend fun saveEmail(email: String)
    suspend fun savePassword(password: String)
    suspend fun getSavedEmail(): String?
    suspend fun getSavedPassword(): String?
    suspend fun saveName(name:String)
    suspend fun getName(): String?
    suspend fun logout()
    fun getNameFlow(): Flow<String?>
}