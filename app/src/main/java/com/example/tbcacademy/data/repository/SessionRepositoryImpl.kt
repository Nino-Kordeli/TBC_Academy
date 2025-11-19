package com.example.tbcacademy.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.tbcacademy.domain.repository.SessionRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "session_prefs")

@Singleton
class SessionRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : SessionRepository {

    companion object {
        private val KEY_TOKEN = stringPreferencesKey("token")
        private val KEY_EMAIL = stringPreferencesKey("email")
        private val KEY_REMEMBER = booleanPreferencesKey("remember_me")
    }

    private val dataStore = context.dataStore

    override suspend fun saveToken(token: String) {
        dataStore.edit { it[KEY_TOKEN] = token }
    }

    override suspend fun saveEmail(email: String) {
        dataStore.edit { it[KEY_EMAIL] = email }
    }

    override suspend fun saveRememberMe(remember: Boolean) {
        dataStore.edit { it[KEY_REMEMBER] = remember }
    }

    override suspend fun readToken(): String? =
        dataStore.data.map { it[KEY_TOKEN] }.first()

    override suspend fun readEmail(): String? =
        dataStore.data.map { it[KEY_EMAIL] }.first()

    override suspend fun isRememberMe(): Boolean =
        dataStore.data.map { it[KEY_REMEMBER] ?: false }.first()

    override suspend fun clearAll() {
        dataStore.edit { it.clear() }
    }

    override fun getTokenSync(): String? = runBlocking { readToken() }
}
