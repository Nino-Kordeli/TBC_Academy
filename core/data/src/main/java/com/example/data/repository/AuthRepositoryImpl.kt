package com.example.data.repository

import com.example.data.datasource.FirebaseAuthDataSource
import com.example.domain.repository.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val dataSource: FirebaseAuthDataSource
) : AuthRepository {
    override suspend fun login(email: String, password: String) {
        dataSource.login(email, password)
    }

    override suspend fun register(email: String, password: String) {
        dataSource.register(email, password)
    }

    override suspend fun logout() {
        dataSource.logout()
    }

    override fun isLoggedIn(): Boolean = dataSource.isLoggedIn()
}