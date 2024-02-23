package com.wizbii.cinematic.journey.domain.use.case.dark.mode

import com.wizbii.cinematic.journey.domain.repository.PreferencesRepository
import kotlinx.coroutines.flow.Flow

class ObserveDarkModePreferenceUseCase(
    private val preferencesRepository: PreferencesRepository,
) {

    operator fun invoke(): Flow<Boolean> =
        preferencesRepository.isDarkModeChanges

}
