package com.wizbii.cinematic.journey.data.repository

import com.wizbii.cinematic.journey.data.source.PreferencesLocalDataSource
import com.wizbii.cinematic.journey.domain.repository.PreferencesRepository
import kotlinx.coroutines.flow.Flow

class DefaultPreferencesRepository(
    private val preferencesLocalDataSource: PreferencesLocalDataSource,
) : PreferencesRepository {

    companion object {

        private const val IS_DARK_MODE_KEY = "isDarkMode"

    }

    override val isDarkModeChanges: Flow<Boolean>
        get() = preferencesLocalDataSource.booleanFlow(IS_DARK_MODE_KEY)

    override suspend fun getDarkMode(): Boolean? =
        preferencesLocalDataSource.getBoolean(IS_DARK_MODE_KEY)

    override suspend fun setDarkMode(isDarkMode: Boolean) {
        preferencesLocalDataSource.setBoolean(IS_DARK_MODE_KEY, isDarkMode)
    }

}
