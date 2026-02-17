package com.example.data.repository

import com.example.data.local.SessionDataStore
import com.example.domain.repository.UserSessionRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class UserSessionRepositoryImpl @Inject constructor(
    private val sessionDataStore: SessionDataStore
) : UserSessionRepository {
    override suspend fun getRememberMe(): Boolean {
        return sessionDataStore.rememberMe.first() ?: false
    }

    override suspend fun getToken(): String? {
        return sessionDataStore.token.first()
    }

    override suspend fun setRememberMe(value: Boolean) {
        sessionDataStore.setRememberMe(value)
    }

    override suspend fun saveEmail(email: String) {
        sessionDataStore.saveEmail(email)
    }

    override suspend fun savePassword(password: String) {
        sessionDataStore.savePassword(password)
    }

    override suspend fun getSavedEmail(): String? {
        return sessionDataStore.email.first()
    }

    override suspend fun getSavedPassword(): String? {
        return sessionDataStore.password.first()
    }
}