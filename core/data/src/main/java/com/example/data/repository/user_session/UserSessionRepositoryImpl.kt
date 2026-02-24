package com.example.data.repository.user_session

import com.example.data.local.SessionDataStore
import com.example.domain.repository.user_session.UserSessionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class UserSessionRepositoryImpl @Inject constructor(
    private val sessionDataStore: SessionDataStore
) : UserSessionRepository {

    override suspend fun saveSession(token: String, rememberMe: Boolean) {
        sessionDataStore.saveSession(token, rememberMe)
    }

    override suspend fun getRememberMe(): Boolean {
        return sessionDataStore.rememberMe.first()
    }

    override suspend fun getToken(): String? {
        return sessionDataStore.token.first()
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

    override suspend fun saveName(name: String) {
        sessionDataStore.saveName(name)
    }

    override suspend fun getName(): String? {
        return sessionDataStore.name.first()
    }

    override suspend fun logout() {
        sessionDataStore.clearSession()
    }

    override fun getNameFlow(): Flow<String?> {
        return sessionDataStore.name
    }
}