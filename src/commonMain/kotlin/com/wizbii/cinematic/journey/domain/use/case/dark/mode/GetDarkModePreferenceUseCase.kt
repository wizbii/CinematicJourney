package com.wizbii.cinematic.journey.domain.use.case.dark.mode

import com.wizbii.cinematic.journey.domain.repository.PreferencesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetDarkModePreferenceUseCase(
    private val preferencesRepository: PreferencesRepository,
) {

    suspend operator fun invoke(): Boolean? =
        withContext(Dispatchers.Default) {
            preferencesRepository.getDarkMode()
        }

}
