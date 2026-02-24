package com.example.data.repository.auth

import com.example.common.resource.Resource
import com.example.data.datasource.FirebaseAuthDataSource
import com.example.data.local.SessionDataStore
import com.example.data.response_handler.auth.HandleLoginResponse
import com.example.data.response_handler.auth.HandleRegisterResponse
import com.example.domain.repository.auth.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val sessionDataStore: SessionDataStore,
    private val handleLoginResponse: HandleLoginResponse,
    private val dataSource: FirebaseAuthDataSource,
    private val handleRegisterResponse: HandleRegisterResponse
) : AuthRepository {

    override suspend fun login(
        email: String,
        password: String
    ): Flow<Resource<String>> {
        return handleLoginResponse.apiCall {
            auth.signInWithEmailAndPassword(email, password).await()
        }
    }

    override suspend fun register(email: String, password: String, rememberMe: Boolean): String {
        val userId = dataSource.register(email, password)

        if (rememberMe) {
            sessionDataStore.saveSession(token = userId, rememberMe = true)
        }

        return userId
    }

    override suspend fun logout() {
        dataSource.logout()
        sessionDataStore.clearSession()
    }

    override fun isLoggedIn() = dataSource.isLoggedIn()
}