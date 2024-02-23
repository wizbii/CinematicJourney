package com.wizbii.cinematic.journey.data.repository

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToOneOrNull
import com.wizbii.cinematic.journey.data.TmdbMoviesQueries
import com.wizbii.cinematic.journey.data.source.TmdbApiDataSource
import com.wizbii.cinematic.journey.domain.entity.TmdbMovie
import com.wizbii.cinematic.journey.domain.entity.TmdbMovieId
import com.wizbii.cinematic.journey.domain.repository.TmdbRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import kotlinx.datetime.Instant

class DefaultTmdbRepository(
    private val tmdbApiDataSource: TmdbApiDataSource,
    private val tmdbMoviesQueries: TmdbMoviesQueries,
) : TmdbRepository {

    override suspend fun cleanupOldTmdbMovies(olderThan: Instant) {
        withContext(Dispatchers.IO) {
            tmdbMoviesQueries.deleteTmdbMoviesOlderThan(fetchDate = olderThan)
        }
    }

    override suspend fun getTmdbBackdropUrlForWidth(backdropPath: String, width: Int): String =
        tmdbApiDataSource.getBackdropUrlForWidth(backdropPath, width)

    override suspend fun getLocalTmdbMovie(id: TmdbMovieId, language: String, maxFetchDate: Instant): Flow<TmdbMovie?> =
        tmdbMoviesQueries
            .readTmdbMovie(id, language)
            .asFlow()
            .mapToOneOrNull(Dispatchers.IO)
            .map { tmdbMovieRecord ->
                if (tmdbMovieRecord == null) return@map null
                TmdbMovie(
                    backdropPath = tmdbMovieRecord.backdropPath,
                    id = tmdbMovieRecord.id,
                    overview = tmdbMovieRecord.overview,
                    posterPath = tmdbMovieRecord.posterPath,
                    releaseDate = tmdbMovieRecord.releaseDate,
                    runtime = tmdbMovieRecord.runtime,
                    tagline = tmdbMovieRecord.tagline,
                    title = tmdbMovieRecord.title,
                )
            }

    override suspend fun getRemoteTmdbMovie(id: TmdbMovieId, language: String): TmdbMovie =
        tmdbApiDataSource.getMovieDetails(id, language).let { details ->
            TmdbMovie(
                backdropPath = details.backdropPath,
                id = details.id,
                overview = details.overview,
                posterPath = details.posterPath,
                releaseDate = details.releaseDate,
                runtime = details.runtime,
                tagline = details.tagline,
                title = details.title,
            )
        }

    override suspend fun getTmdbPosterUrlForWidth(posterPath: String, width: Int): String =
        tmdbApiDataSource.getPosterUrlForWidth(posterPath, width)

    override suspend fun setLocalTmdbMovie(tmdbMovie: TmdbMovie, fetchDate: Instant, language: String) {
        tmdbMoviesQueries.createOrUpdateTmdbMovie(
            backdropPath = tmdbMovie.backdropPath,
            fetchDate = fetchDate,
            id = tmdbMovie.id,
            language = language,
            overview = tmdbMovie.overview,
            posterPath = tmdbMovie.posterPath,
            releaseDate = tmdbMovie.releaseDate,
            runtime = tmdbMovie.runtime,
            tagline = tmdbMovie.tagline,
            title = tmdbMovie.title,
        )
    }

}
