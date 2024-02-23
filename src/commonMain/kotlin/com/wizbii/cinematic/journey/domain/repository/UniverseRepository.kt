package com.wizbii.cinematic.journey.domain.repository

import com.wizbii.cinematic.journey.domain.entity.JsonUniverse
import com.wizbii.cinematic.journey.domain.entity.Universe
import com.wizbii.cinematic.journey.domain.entity.UniverseId
import kotlinx.coroutines.flow.Flow

interface UniverseRepository {

    suspend fun createOrUpdateUniverses(universes: List<JsonUniverse>)

    fun getUniverse(id: UniverseId): Flow<Universe>

    fun getUniverses(): Flow<Set<Universe>>

}
