package com.wizbii.cinematic.journey.data.repository

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOne
import com.wizbii.cinematic.journey.data.MovieRecord
import com.wizbii.cinematic.journey.data.MoviesQueries
import com.wizbii.cinematic.journey.data.PrerequisitesQueries
import com.wizbii.cinematic.journey.domain.entity.JsonMovie
import com.wizbii.cinematic.journey.domain.entity.LocalMovie
import com.wizbii.cinematic.journey.domain.entity.MovieId
import com.wizbii.cinematic.journey.domain.entity.UniverseId
import com.wizbii.cinematic.journey.domain.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class DefaultMovieRepository(
    private val movieQueries: MoviesQueries,
    private val prerequisiteQueries: PrerequisitesQueries,
) : MovieRepository {

    override suspend fun createOrUpdateMovies(universeId: UniverseId, movies: List<JsonMovie>) {
        withContext(Dispatchers.IO) {
            movieQueries.transaction {
                movies.forEach { movie ->
                    movieQueries.createOrUpdateMovie(
                        id = movie.id,
                        originalTitle = movie.originalTitle,
                        releaseDate = movie.originalDate,
                        tmdbId = movie.tmdbId,
                        universeId = universeId,
                    )
                }
            }
            prerequisiteQueries.transaction {
                movies.forEach { movie ->
                    movie.prerequisites.forEach { prerequisiteMovieId ->
                        prerequisiteQueries.createPrerequisiteIfNotExist(
                            movieId = movie.id,
                            prerequisiteMovieId = prerequisiteMovieId,
                        )
                    }
                }
            }
        }
    }

    override fun getMovie(id: MovieId): Flow<LocalMovie> =
        getMovieRecord(id).combine(getPrerequisiteMoviesIds(id), ::buildMovie)

    override fun getMovies(): Flow<Set<LocalMovie>> =
        movieQueries
            .readMovies()
            .asFlow()
            .mapToList(Dispatchers.IO)
            .mapToMovies()

    override fun getMoviesForUniverse(universeId: UniverseId): Flow<Set<LocalMovie>> =
        movieQueries
            .readMoviesByUniverseId(universeId)
            .asFlow()
            .mapToList(Dispatchers.IO)
            .mapToMovies()

    override suspend fun setMovieWatched(id: MovieId, watched: Boolean) =
        withContext(Dispatchers.IO) {
            movieQueries.updateMovieWatched(watched, id)
        }

    private fun buildMovie(
        record: MovieRecord,
        prerequisiteIds: Set<MovieId>,
    ) = LocalMovie(
        id = record.id,
        originalTitle = record.originalTitle,
        prerequisitesIds = prerequisiteIds,
        releaseDate = record.releaseDate,
        tmdbId = record.tmdbId,
        universeId = record.universeId,
        watched = record.watched,
    )

    private fun getMovieRecord(id: MovieId): Flow<MovieRecord> =
        movieQueries
            .readMovieById(id)
            .asFlow()
            .mapToOne(Dispatchers.IO)

    private fun getPrerequisiteMoviesIds(id: MovieId): Flow<Set<MovieId>> =
        prerequisiteQueries
            .readPrerequisites(id)
            .asFlow()
            .mapToList(Dispatchers.IO)
            .map(List<MovieId>::toSet)

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun Flow<List<MovieRecord>>.mapToMovies(): Flow<Set<LocalMovie>> = this
        .map { records ->
            records.map { record ->
                flowOf(record).combine(getPrerequisiteMoviesIds(record.id), ::buildMovie)
            }
        }
        .flatMapLatest { movieFlows ->
            if (movieFlows.isEmpty()) {
                flowOf(emptySet())
            } else {
                combine(movieFlows, Array<LocalMovie>::toSet)
            }
        }

}
