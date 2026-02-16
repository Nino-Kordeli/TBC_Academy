package com.example.domain.repository

interface AuthRepository {
    suspend fun login(email: String, password: String, rememberMe: Boolean)
    suspend fun register(email: String, password: String, rememberMe: Boolean)
    suspend fun logout()
    fun isLoggedIn(): Boolean
}