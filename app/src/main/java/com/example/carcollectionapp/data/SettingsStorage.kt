package com.example.carcollectionapp.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingsStorage(private val context: Context) {
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("settings")
        private val SETTINGS_KEY = booleanPreferencesKey("subscriptionEnabled")
        private val VIEWS_KEY = intPreferencesKey("viewCount")
        private val ADD_KEY = intPreferencesKey("addCount")
    }

    val getSettings: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[SETTINGS_KEY] ?: false
    }
    val getViewCount: Flow<Int> = context.dataStore.data.map { preferences ->
        preferences[VIEWS_KEY] ?: 3
    }
    val getAddCount: Flow<Int> = context.dataStore.data.map { preferences ->
        preferences[ADD_KEY] ?: 2
    }

    suspend fun saveSettings(subscriptionEnabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[SETTINGS_KEY] = subscriptionEnabled
        }
    }

    suspend fun saveViewCount(count: Int) {
        context.dataStore.edit { preferences ->
            preferences[VIEWS_KEY] = count
        }
    }

    suspend fun saveAddCount(count: Int) {
        context.dataStore.edit { preferences ->
            preferences[ADD_KEY] = count
        }
    }
}