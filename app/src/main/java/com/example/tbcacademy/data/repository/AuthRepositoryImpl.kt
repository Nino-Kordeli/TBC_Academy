package com.example.tbcacademy.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import com.example.tbcacademy.data.common.HandleFirebaseResponse
import com.example.tbcacademy.data.common.HandleResponse
import com.example.tbcacademy.data.common.Resource
import com.example.tbcacademy.data.firebase.awaitResult
import com.example.tbcacademy.data.mapper.base.asResource
import com.example.tbcacademy.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class AuthRepositoryImpl(
    private val auth: FirebaseAuth,
    private val dataStore: DataStore<Preferences>,
    private val handleResponse: HandleFirebaseResponse,
) : AuthRepository {

    private val IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")

    override fun register(email: String, password: String): Flow<Resource<Boolean>> =
        handleResponse.authCall {
            auth.createUserWithEmailAndPassword(email, password).awaitResult()
            dataStore.edit { prefs -> prefs[IS_LOGGED_IN] = true }
            true
        }

    override fun login(email: String, password: String): Flow<Resource<Boolean>> =
        handleResponse.authCall {
            auth.signInWithEmailAndPassword(email, password).awaitResult()
            dataStore.edit { prefs -> prefs[IS_LOGGED_IN] = true }
            true
        }

    override fun isUserLoggedIn() = dataStore.data
        .catch { emit(emptyPreferences()) }
        .map { prefs -> prefs[IS_LOGGED_IN] ?: (auth.currentUser != null) }

    override suspend fun signOut() {
        auth.signOut()
        dataStore.edit { prefs -> prefs[IS_LOGGED_IN] = false }
    }
}
