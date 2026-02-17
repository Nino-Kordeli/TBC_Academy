package com.example.data.repository

import com.example.data.datasource.FirebaseAuthDataSource
import com.example.data.local.SessionDataStore
import com.example.domain.repository.auth.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val sessionDataStore: SessionDataStore,
    private val dataSource: FirebaseAuthDataSource
) : AuthRepository {
    override suspend fun login(email: String, password: String, rememberMe: Boolean) {
        val token = dataSource.login(email, password)
        if (rememberMe) {
            sessionDataStore.saveSession(token = token, rememberMe = true)
        }
    }

    override suspend fun register(email: String, password: String, rememberMe: Boolean) {
        val token = dataSource.register(email, password)
        if (rememberMe) {
            sessionDataStore.saveSession(token = token, rememberMe = true)
        }
    }

    override suspend fun logout() {
        dataSource.logout()
        sessionDataStore.clearSession()
    }

    override fun isLoggedIn(): Boolean = dataSource.isLoggedIn()
}