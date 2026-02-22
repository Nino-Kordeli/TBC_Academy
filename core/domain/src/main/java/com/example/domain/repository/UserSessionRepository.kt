package com.example.domain.repository

interface UserSessionRepository {
    suspend fun saveSession(token: String, rememberMe: Boolean)
    suspend fun getRememberMe(): Boolean
    suspend fun getToken(): String?
    suspend fun saveEmail(email: String)
    suspend fun savePassword(password: String)
    suspend fun getSavedEmail(): String?
    suspend fun getSavedPassword(): String?
}