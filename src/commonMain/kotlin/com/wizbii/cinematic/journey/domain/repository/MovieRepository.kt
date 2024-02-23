package com.wizbii.cinematic.journey.domain.repository

import com.wizbii.cinematic.journey.domain.entity.JsonMovie
import com.wizbii.cinematic.journey.domain.entity.LocalMovie
import com.wizbii.cinematic.journey.domain.entity.MovieId
import com.wizbii.cinematic.journey.domain.entity.UniverseId
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    suspend fun createOrUpdateMovies(universeId: UniverseId, movies: List<JsonMovie>)

    fun getMovie(id: MovieId): Flow<LocalMovie>

    fun getMovies(): Flow<Set<LocalMovie>>

    fun getMoviesForUniverse(universeId: UniverseId): Flow<Set<LocalMovie>>

    suspend fun setMovieWatched(id: MovieId, watched: Boolean)

}
