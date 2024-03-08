package com.wizbii.cinematic.journey.domain.use.case

import com.wizbii.cinematic.journey.domain.entity.Movie
import com.wizbii.cinematic.journey.domain.entity.MovieId
import com.wizbii.cinematic.journey.domain.repository.MovieRepository
import com.wizbii.cinematic.journey.domain.repository.TmdbRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf

class ObserveMovieUseCase(
    private val movieRepository: MovieRepository,
    private val tmdbRepository: TmdbRepository,
) {

    operator fun invoke(movieId: MovieId, language: String): Flow<Movie> =
        @OptIn(ExperimentalCoroutinesApi::class)
        movieRepository
            .getMovie(movieId)
            .flatMapLatest { localMovie ->
                flowOf(localMovie).combine(
                    flow = tmdbRepository.getLocalTmdbMovie(
                        id = localMovie.tmdbId,
                        language = language,
                    ),
                    transform = ::Movie,
                )
            }

}
