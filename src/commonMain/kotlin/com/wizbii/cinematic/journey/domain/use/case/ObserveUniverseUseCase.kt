package com.wizbii.cinematic.journey.domain.use.case

import com.wizbii.cinematic.journey.domain.entity.Universe
import com.wizbii.cinematic.journey.domain.entity.UniverseId
import com.wizbii.cinematic.journey.domain.repository.UniverseRepository
import kotlinx.coroutines.flow.Flow

class ObserveUniverseUseCase(
    private val universeRepository: UniverseRepository,
) {

    operator fun invoke(universeId: UniverseId): Flow<Universe> =
        universeRepository.getUniverse(universeId)

}
