package com.example.tbcacademy.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserPreferences @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    companion object {
        val KEY_EMAIL = stringPreferencesKey("key_email")
        val KEY_IS_LOGGED_IN = booleanPreferencesKey("key_is_logged_in")
    }

    suspend fun saveLogin(email: String) {
        dataStore.edit { prefs ->
            prefs[KEY_EMAIL] = email
            prefs[KEY_IS_LOGGED_IN] = true
        }
    }

    suspend fun clearLogin() {
        dataStore.edit { prefs ->
            prefs.remove(KEY_EMAIL)
            prefs.remove(KEY_IS_LOGGED_IN)
        }
    }

    val isLoggedIn = dataStore.data.map { prefs ->
        prefs[KEY_IS_LOGGED_IN] ?: false
    }

    val getEmail = dataStore.data.map { prefs ->
        prefs[KEY_EMAIL] ?: ""
    }
}