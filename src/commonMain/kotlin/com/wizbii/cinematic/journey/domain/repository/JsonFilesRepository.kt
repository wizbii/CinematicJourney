package com.wizbii.cinematic.journey.domain.repository

import com.wizbii.cinematic.journey.domain.entity.JsonMovie
import com.wizbii.cinematic.journey.domain.entity.JsonUniverse
import com.wizbii.cinematic.journey.domain.entity.UniverseId

interface JsonFilesRepository {

    suspend fun getJsonMovies(universeId: UniverseId): List<JsonMovie>

    suspend fun getJsonUniverses(): List<JsonUniverse>

}
