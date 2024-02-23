package com.wizbii.cinematic.journey.domain.repository

import kotlinx.coroutines.flow.Flow

interface PreferencesRepository {

    val isDarkModeChanges: Flow<Boolean>

    suspend fun getDarkMode(): Boolean?

    suspend fun setDarkMode(isDarkMode: Boolean)

}
