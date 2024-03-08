package com.wizbii.cinematic.journey.domain.repository

import com.wizbii.cinematic.journey.domain.entity.TmdbMovie
import com.wizbii.cinematic.journey.domain.entity.TmdbMovieId
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.Instant

interface TmdbRepository {

    suspend fun cleanupOldTmdbMovies(olderThan: Instant)

    suspend fun getTmdbBackdropUrlForWidth(backdropPath: String, width: Int): String

    suspend fun getLocalTmdbMovie(
        id: TmdbMovieId,
        language: String,
        maxFetchDate: Instant = Instant.DISTANT_PAST,
    ): Flow<TmdbMovie?>

    suspend fun getRemoteTmdbMovie(id: TmdbMovieId, language: String): TmdbMovie

    suspend fun getTmdbPosterUrlForWidth(posterPath: String, width: Int): String

    suspend fun setLocalTmdbMovie(tmdbMovie: TmdbMovie, fetchDate: Instant, language: String)

}
