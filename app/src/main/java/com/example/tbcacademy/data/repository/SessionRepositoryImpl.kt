package com.example.tbcacademy.data.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.tbcacademy.domain.repository.SessionRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

private val Context.dataStore by preferencesDataStore(name = "session_prefs")

class SessionRepositoryImpl(private val context: Context) : SessionRepository {

    companion object {
        private val KEY_TOKEN = stringPreferencesKey("token")
        private val KEY_EMAIL = stringPreferencesKey("email")
        private val KEY_REMEMBER_ME = stringPreferencesKey("remember_me")
    }

    private val dataStore = context.dataStore

    override suspend fun saveToken(token: String) {
        dataStore.edit { prefs ->
            prefs[KEY_TOKEN] = token
        }
    }

    override suspend fun saveEmail(email: String) {
        dataStore.edit { prefs ->
            prefs[KEY_EMAIL] = email
        }
    }

    override suspend fun saveRememberMe(remember: Boolean) {
        dataStore.edit { prefs ->
            prefs[KEY_REMEMBER_ME] = remember.toString()
        }
    }

    fun getToken(): String? = runBlocking {
        dataStore.data.map { it[KEY_TOKEN] }.first()
    }

    override suspend fun readToken(): String? =
        dataStore.data.map { it[KEY_TOKEN] }.first()

    override suspend fun readEmail(): String? =
        dataStore.data.map { it[KEY_EMAIL] }.first()

    override suspend fun isRememberMe(): Boolean =
        dataStore.data.map { it[KEY_REMEMBER_ME]?.toBoolean() ?: false }.first()

    override suspend fun clearAll() {
        dataStore.edit { it.clear() }
    }
}