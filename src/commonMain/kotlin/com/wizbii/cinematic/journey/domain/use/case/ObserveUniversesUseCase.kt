package com.wizbii.cinematic.journey.domain.use.case

import com.wizbii.cinematic.journey.domain.entity.Universe
import com.wizbii.cinematic.journey.domain.repository.UniverseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ObserveUniversesUseCase(
    private val universeRepository: UniverseRepository,
) {

    operator fun invoke(): Flow<List<Universe>> =
        universeRepository
            .getUniverses()
            .map { universes ->
                universes.sortedBy { universe ->
                    universe.id.value
                }
            }

}
