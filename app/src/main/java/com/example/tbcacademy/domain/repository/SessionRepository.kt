package com.example.tbcacademy.domain.repository

interface SessionRepository {
    suspend fun saveToken(token: String)
    suspend fun saveEmail(email: String)
    suspend fun saveRememberMe(remember: Boolean)
    suspend fun readToken(): String?
    suspend fun readEmail(): String?
    suspend fun isRememberMe(): Boolean
    suspend fun clearAll()
    fun getTokenSync(): String?

    suspend fun hasValidSession(): Boolean {
        val hasToken = readToken() != null
        val shouldRemember = isRememberMe()
        return hasToken && shouldRemember
    }
}