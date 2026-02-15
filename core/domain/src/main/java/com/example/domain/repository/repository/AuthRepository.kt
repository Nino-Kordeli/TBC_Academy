package com.example.domain.repository.repository

interface AuthRepository {
    suspend fun login(email: String, password: String)
    suspend fun register(email:String,password:String)
    suspend fun logout()
    fun isLoggedIn(): Boolean
}