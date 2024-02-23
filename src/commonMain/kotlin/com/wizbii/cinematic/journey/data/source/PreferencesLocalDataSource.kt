package com.wizbii.cinematic.journey.data.source

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.mapNotNull

class PreferencesLocalDataSource(
    private val dataStore: DataStore<Preferences>,
) {

    fun booleanFlow(key: String): Flow<Boolean> =
        dataStore.data.mapNotNull { preferences ->
            preferences[booleanPreferencesKey(key)]
        }

    suspend fun getBoolean(key: String): Boolean? =
        dataStore.data.firstOrNull()?.get(booleanPreferencesKey(key))

    suspend fun getString(key: String): String? =
        dataStore.data.firstOrNull()?.get(stringPreferencesKey(key))

    suspend fun setBoolean(key: String, value: Boolean) {
        dataStore.edit { preferences ->
            preferences[booleanPreferencesKey(key)] = value
        }
    }

    suspend fun setString(key: String, value: String) {
        dataStore.edit { preferences ->
            preferences[stringPreferencesKey(key)] = value
        }
    }

}
