package com.wizbii.cinematic.journey.domain.use.case.dark.mode

import com.wizbii.cinematic.journey.domain.repository.PreferencesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

// Ensure the platform dark mode matches the stored dark mode at launch
class AlignPlatformDarkModeUseCase(
    private val preferencesRepository: PreferencesRepository,
) {

    suspend operator fun invoke() {
        val isDarkMode = withContext(Dispatchers.IO) {
            preferencesRepository.getDarkMode()
        }
        if (isDarkMode == null) return
        withContext(Dispatchers.Main) {
            setPlatformDarkMode(isDarkMode)
        }
    }

}
