package com.wizbii.cinematic.journey.domain.use.case.start

import com.wizbii.cinematic.journey.domain.repository.JsonFilesRepository
import com.wizbii.cinematic.journey.domain.repository.MovieRepository
import com.wizbii.cinematic.journey.domain.repository.UniverseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class InitializeOrUpdateDatabaseUseCase(
    private val jsonFilesRepository: JsonFilesRepository,
    private val movieRepository: MovieRepository,
    private val universeRepository: UniverseRepository,
) {

    suspend operator fun invoke() = withContext(Dispatchers.Default) {
        val jsonUniverses = jsonFilesRepository.getJsonUniverses()
        universeRepository.createOrUpdateUniverses(jsonUniverses)
        jsonUniverses.forEach { jsonUniverse ->
            val universeJsonMovies = jsonFilesRepository.getJsonMovies(jsonUniverse.id)
            movieRepository.createOrUpdateMovies(jsonUniverse.id, universeJsonMovies)
        }
    }

}
