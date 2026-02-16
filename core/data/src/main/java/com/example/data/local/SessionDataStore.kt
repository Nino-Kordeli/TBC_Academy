package com.example.data.local

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.sessionDataStore by preferencesDataStore(name = "session")

class SessionDataStore(
    private val context: Context
) {

    companion object {
        private val TOKEN = stringPreferencesKey("token")
        private val REMEMBER_ME = booleanPreferencesKey("remember_me")
    }

    suspend fun saveSession(token: String, rememberMe: Boolean) {
        context.sessionDataStore.edit {
            it[TOKEN] = token
            it[REMEMBER_ME] = rememberMe
        }
    }

    val token: Flow<String?> =
        context.sessionDataStore.data.map { prefs ->
            prefs[TOKEN]
        }

    val rememberMe: Flow<Boolean?> =
        context.sessionDataStore.data.map { prefs ->
            prefs[REMEMBER_ME] ?: false
        }

    suspend fun clearSession() {
        context.sessionDataStore.edit {
            it.clear()
        }
    }
}