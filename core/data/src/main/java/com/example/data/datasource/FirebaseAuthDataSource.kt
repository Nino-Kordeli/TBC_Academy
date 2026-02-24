package com.example.data.datasource

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseAuthDataSource @Inject constructor(
    private val auth: FirebaseAuth
) {
    suspend fun register(email: String, password: String): String {
        val result = auth.createUserWithEmailAndPassword(email, password).await()
        return result.user?.uid ?: throw IllegalStateException("User is null")
    }

    fun isLoggedIn() = auth.currentUser != null

    fun logout() = auth.signOut()
}