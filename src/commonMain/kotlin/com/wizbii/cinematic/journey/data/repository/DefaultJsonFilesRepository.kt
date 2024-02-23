package com.wizbii.cinematic.journey.data.repository

import com.wizbii.cinematic.journey.data.source.MovieJsonDataSource
import com.wizbii.cinematic.journey.data.source.UniverseJsonDataSource
import com.wizbii.cinematic.journey.domain.entity.JsonMovie
import com.wizbii.cinematic.journey.domain.entity.JsonUniverse
import com.wizbii.cinematic.journey.domain.entity.UniverseId
import com.wizbii.cinematic.journey.domain.repository.JsonFilesRepository

class DefaultJsonFilesRepository(
    private val movieJsonDataSource: MovieJsonDataSource,
    private val universeJsonDataSource: UniverseJsonDataSource,
) : JsonFilesRepository {

    override suspend fun getJsonMovies(universeId: UniverseId): List<JsonMovie> =
        movieJsonDataSource.readJsonMovies(universeId)

    override suspend fun getJsonUniverses(): List<JsonUniverse> =
        universeJsonDataSource.readJsonUniverses()

}
