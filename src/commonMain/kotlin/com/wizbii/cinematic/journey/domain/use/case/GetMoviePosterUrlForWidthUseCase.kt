package com.wizbii.cinematic.journey.domain.use.case

import com.wizbii.cinematic.journey.domain.repository.TmdbRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetMoviePosterUrlForWidthUseCase(
    private val tmdbRepository: TmdbRepository,
) {

    suspend operator fun invoke(posterPath: String, width: Int): String =
        withContext(Dispatchers.Default) {
            tmdbRepository.getTmdbPosterUrlForWidth(posterPath, width)
        }

}
