package com.example.tbcacademy.domain.repository

interface TokenRepository {
    suspend fun saveToken(token: String)
    suspend fun readToken(): String?
    suspend fun saveUsername(username: String)
    suspend fun readUsername(): String?
    suspend fun saveEmail(email: String)
    suspend fun readEmail(): String?
    suspend fun clearAll()
    suspend fun clearToken()
}