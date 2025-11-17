package com.example.tbcacademy.data.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.tbcacademy.domain.repository.SessionRepository
import com.example.tbcacademy.utils.SessionManager
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "session_prefs")

class SessionRepositoryImpl(private val context: Context) : SessionRepository {
    private val dataStore = context.dataStore
    private val KEY_TOKEN = stringPreferencesKey("token")
    private val KEY_EMAIL = stringPreferencesKey("email")
    fun getToken(): String? = SessionManager.getToken(context)
    fun isRememberMe(): Boolean = SessionManager.isRememberMe(context)
    fun clear() = SessionManager.clear(context)
    override suspend fun saveToken(token: String) = dataStore.edit { it[KEY_TOKEN] = token }
    override suspend fun saveEmail(email: String) = dataStore.edit { it[KEY_EMAIL] = email }
    override suspend fun readToken(): String? = dataStore.data.map { it[KEY_TOKEN] }.first()
    override suspend fun readEmail(): String? = dataStore.data.map { it[KEY_EMAIL] }.first()
    fun getEmail(): String? = SessionManager.getSavedEmail(context)
    override suspend fun clearAll() = dataStore.edit { it.clear() }
}