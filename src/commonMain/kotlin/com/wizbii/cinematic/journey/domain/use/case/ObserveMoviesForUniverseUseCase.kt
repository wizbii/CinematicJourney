package com.wizbii.cinematic.journey.domain.use.case

import com.wizbii.cinematic.journey.domain.entity.Movie
import com.wizbii.cinematic.journey.domain.entity.UniverseId
import com.wizbii.cinematic.journey.domain.repository.MovieRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf

class ObserveMoviesForUniverseUseCase(
    private val movieRepository: MovieRepository,
    private val observeMovieUseCase: ObserveMovieUseCase,
) {

    operator fun invoke(universeId: UniverseId, language: String): Flow<List<Movie>> =
        @OptIn(ExperimentalCoroutinesApi::class)
        movieRepository
            .getMoviesForUniverse(universeId)
            .flatMapLatest { localMovies ->
                if (localMovies.isEmpty()) {
                    flowOf(emptyList())
                } else {
                    combine(
                        flows = localMovies.map { observeMovieUseCase(it.id, language) },
                        transform = { it.toList().sortedBy(Movie::releaseDate) },
                    )
                }
            }

}
