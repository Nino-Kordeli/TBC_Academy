package com.example.tbcacademy.data.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.tbcacademy.domain.repository.TokenRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore("auth_prefs")

private val KEY_TOKEN = stringPreferencesKey("auth_token")
private val KEY_USERNAME = stringPreferencesKey("username")
private val KEY_EMAIL = stringPreferencesKey("email")

class TokenRepositoryImpl(private val context: Context) : TokenRepository {

    override suspend fun saveToken(token: String) {
        context.dataStore.edit { prefs ->
            prefs[KEY_TOKEN] = token
        }
    }

    override suspend fun readToken(): String? {
        return context.dataStore.data.map { it[KEY_TOKEN] }.first()
    }

    override suspend fun saveUsername(username: String) {
        context.dataStore.edit { it[KEY_USERNAME] = username }
    }

    override suspend fun readUsername(): String? =
        context.dataStore.data.map { it[KEY_USERNAME] }.first()

    override suspend fun saveEmail(email: String) {
        context.dataStore.edit { it[KEY_EMAIL] = email }
    }

    override suspend fun readEmail(): String? =
        context.dataStore.data.map { it[KEY_EMAIL] }.first()

    override suspend fun clearAll() {
        context.dataStore.edit { it.clear() }
    }

    override suspend fun clearToken() {
        context.dataStore.edit { prefs ->
            prefs.remove(KEY_TOKEN)
        }
    }
}
