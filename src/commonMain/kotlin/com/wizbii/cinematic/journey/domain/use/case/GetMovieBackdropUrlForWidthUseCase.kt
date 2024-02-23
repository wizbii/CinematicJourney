package com.wizbii.cinematic.journey.domain.use.case

import com.wizbii.cinematic.journey.domain.repository.TmdbRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetMovieBackdropUrlForWidthUseCase(
    private val tmdbRepository: TmdbRepository,
) {

    suspend operator fun invoke(backdropPath: String, width: Int): String =
        withContext(Dispatchers.Default) {
            tmdbRepository.getTmdbBackdropUrlForWidth(backdropPath, width)
        }

}
