package com.example.tbcacademy.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserPreferences @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    companion object {
        val KEY_EMAIL = stringPreferencesKey("key_email")
    }

    suspend fun saveEmail(email: String) {
        dataStore.edit { prefs ->
            prefs[KEY_EMAIL] = email
        }
    }

    suspend fun clearEmail() {
        dataStore.edit { prefs ->
            prefs.remove(KEY_EMAIL)
        }
    }

    val getEmail = dataStore.data.map { prefs ->
        prefs[KEY_EMAIL] ?: ""
    }
}