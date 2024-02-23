package com.wizbii.cinematic.journey.domain.use.case

import com.wizbii.cinematic.journey.domain.entity.MovieId
import com.wizbii.cinematic.journey.domain.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SetMovieWatchedUseCase(
    private val movieRepository: MovieRepository,
) {

    suspend operator fun invoke(movieId: MovieId, watched: Boolean) {
        withContext(Dispatchers.Default) {
            movieRepository.setMovieWatched(movieId, watched)
        }
    }

}
