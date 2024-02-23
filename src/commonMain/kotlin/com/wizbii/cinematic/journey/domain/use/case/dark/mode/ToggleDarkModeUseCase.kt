package com.wizbii.cinematic.journey.domain.use.case.dark.mode

import com.wizbii.cinematic.journey.domain.repository.PreferencesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ToggleDarkModeUseCase(
    private val preferencesRepository: PreferencesRepository,
) {

    suspend operator fun invoke() = withContext(Dispatchers.Default) {
        val isDarkMode = preferencesRepository.getDarkMode() ?: return@withContext
        preferencesRepository.setDarkMode(!isDarkMode)
        setPlatformDarkMode(!isDarkMode)
    }

}
