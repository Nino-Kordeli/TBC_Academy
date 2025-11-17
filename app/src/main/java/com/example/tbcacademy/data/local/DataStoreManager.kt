package com.example.tbcacademy.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore("session_prefs")


class DataStoreManager(private val context: Context) {
    private val TOKEN_KEY = stringPreferencesKey("auth_token")
    private val EMAIL_KEY = stringPreferencesKey("user_email")
    private val USERNAME_KEY = stringPreferencesKey("username")


    suspend fun saveToken(token: String) {
        context.dataStore.edit { it[TOKEN_KEY] = token }
    }


    suspend fun readToken(): String? = context.dataStore.data.map { it[TOKEN_KEY] }.first()


    suspend fun clearToken() {
        context.dataStore.edit { it.remove(TOKEN_KEY) }
    }

    suspend fun saveEmail(email: String) {
        context.dataStore.edit { it[EMAIL_KEY] = email }
    }

    suspend fun readEmail(): String? = context.dataStore.data.map { it[EMAIL_KEY] }.first()

    suspend fun saveUsername(name: String) {
        context.dataStore.edit { it[USERNAME_KEY] = name }
    }

    suspend fun readUsername(): String? = context.dataStore.data.map { it[USERNAME_KEY] }.first()


    suspend fun clearAll() {
        context.dataStore.edit { it.clear() }
    }
}