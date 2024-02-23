package com.wizbii.cinematic.journey.domain.entity

import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

@Serializable
data class Movie(
    val backdropPath: String?,
    val id: MovieId,
    val overview: String?,
    val posterPath: String?,
    val prerequisitesIds: Set<MovieId>,
    val releaseDate: LocalDate,
    val runtime: Int?,
    val tagline: String?,
    val title: String,
    val tmdbId: TmdbMovieId,
    val universeId: UniverseId,
    val watched: Boolean,
) {

    constructor(localMovie: LocalMovie, tmdbMovie: TmdbMovie?) : this(
        backdropPath = tmdbMovie?.backdropPath,
        id = localMovie.id,
        overview = tmdbMovie?.overview,
        posterPath = tmdbMovie?.posterPath,
        prerequisitesIds = localMovie.prerequisitesIds,
        releaseDate = tmdbMovie?.releaseDate ?: localMovie.releaseDate,
        runtime = tmdbMovie?.runtime,
        tagline = tmdbMovie?.tagline,
        title = tmdbMovie?.title ?: localMovie.originalTitle,
        tmdbId = localMovie.tmdbId,
        universeId = localMovie.universeId,
        watched = localMovie.watched,
    )

}
