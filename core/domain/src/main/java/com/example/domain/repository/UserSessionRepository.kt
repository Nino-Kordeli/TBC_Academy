package com.example.domain.repository

interface UserSessionRepository {
    suspend fun getRememberMe(): Boolean
    suspend fun getToken(): String?
    suspend fun setRememberMe(value: Boolean)
    suspend fun saveEmail(email: String)
    suspend fun savePassword(password: String)
    suspend fun getSavedEmail(): String?
    suspend fun getSavedPassword(): String?
}