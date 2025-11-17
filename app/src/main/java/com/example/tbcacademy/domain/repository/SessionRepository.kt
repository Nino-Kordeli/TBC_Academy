package com.example.tbcacademy.domain.repository

import androidx.datastore.preferences.core.Preferences

interface SessionRepository {
    suspend fun saveToken(token: String): Preferences
    suspend fun saveEmail(email: String): Preferences
    suspend fun readToken(): String?
    suspend fun readEmail(): String?
    suspend fun clearAll(): Preferences
}