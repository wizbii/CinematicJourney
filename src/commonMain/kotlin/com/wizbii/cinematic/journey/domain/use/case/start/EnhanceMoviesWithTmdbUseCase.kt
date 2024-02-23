package com.wizbii.cinematic.journey.domain.use.case.start

import com.wizbii.cinematic.journey.domain.entity.TmdbMovie
import com.wizbii.cinematic.journey.domain.repository.MovieRepository
import com.wizbii.cinematic.journey.domain.repository.TmdbRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock

class EnhanceMoviesWithTmdbUseCase(
    private val movieRepository: MovieRepository,
    private val tmdbRepository: TmdbRepository,
) {

    suspend operator fun invoke(language: String) = withContext(Dispatchers.Default) {

        val now = Clock.System.now()
        val maxAge = now - TmdbMovie.cacheDuration
        tmdbRepository.cleanupOldTmdbMovies(olderThan = maxAge)

        val localMovies = movieRepository.getMovies().first()
        localMovies.forEach { localMovie ->
            val localTmdbMovie = tmdbRepository
                .getLocalTmdbMovie(id = localMovie.tmdbId, language = language, maxFetchDate = maxAge)
                .firstOrNull()
            if (localTmdbMovie != null) return@forEach
            val remoteTmdbMovie = tmdbRepository.getRemoteTmdbMovie(id = localMovie.tmdbId, language = language)
            val tmdbMovie = remoteTmdbMovie.copy(
                overview = remoteTmdbMovie.overview?.sanitize(),
                tagline = remoteTmdbMovie.tagline?.sanitize(),
                title = remoteTmdbMovie.title?.sanitize(),
            )
            tmdbRepository.setLocalTmdbMovie(tmdbMovie = tmdbMovie, fetchDate = now, language = language)
        }

    }

    /**
     * Sanitize text to prevent display issues.
     *
     * - inserts a non-breaking thin space before high punctuation marks
     * - replaces ... with …
     */
    private fun String.sanitize(): String = this
        .replace(
            regex = " ([:;?!])".toRegex(),
            replacement = " $1",
        )
        .replace("...", "…")

}
