package com.example.carcollectionapp.data

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDateTime
import java.time.ZoneOffset
import javax.inject.Inject

class SettingsStorage  @Inject constructor(private val context: Application) {
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("settings")
        private val SETTINGS_KEY = stringPreferencesKey("subscriptionTimeEpoch")
        private val VIEWS_KEY = intPreferencesKey("viewCount")
        private val ADD_KEY = intPreferencesKey("addCount")
    }

    val getSettings: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[SETTINGS_KEY] ?: LocalDateTime.now().toEpochSecond(ZoneOffset.UTC).toString()
    }
    val getViewCount: Flow<Int> = context.dataStore.data.map { preferences ->
        preferences[VIEWS_KEY] ?: 3
    }
    val getAddCount: Flow<Int> = context.dataStore.data.map { preferences ->
        preferences[ADD_KEY] ?: 2
    }

    suspend fun saveSettings(subscriptionTime: String) {
        context.dataStore.edit { preferences ->
            preferences[SETTINGS_KEY] = subscriptionTime
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