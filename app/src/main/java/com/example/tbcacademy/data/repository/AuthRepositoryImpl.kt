package com.example.tbcacademy.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import com.example.tbcacademy.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")

class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val dataStore: DataStore<Preferences>
) : AuthRepository {

    override suspend fun register(email: String, password: String): Result<Unit> {
        return try {
            val task = auth.createUserWithEmailAndPassword(email, password).awaitResult()
            dataStore.edit { prefs -> prefs[IS_LOGGED_IN] = true }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun login(email: String, password: String): Result<Unit> {
        return try {
            auth.signInWithEmailAndPassword(email, password).awaitResult()
            dataStore.edit { prefs -> prefs[IS_LOGGED_IN] = true }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun signOut() {
        auth.signOut()
        dataStore.edit { prefs -> prefs[IS_LOGGED_IN] = false }
    }

    override fun isUserLoggedIn() = dataStore.data
        .catch { emit(emptyPreferences()) }
        .map { prefs -> prefs[IS_LOGGED_IN] ?: (auth.currentUser != null) }
}
