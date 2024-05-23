package com.wizbii.cinematic.journey.domain.entity

import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

// C’est le modèle UI
@Serializable
data class Movie(
    val backdropPath: String?,
    val budget: Int?,
    val id: MovieId,
    val overview: String?,
    val posterPath: String?,
    val prerequisitesIds: Set<MovieId>,
    val releaseDate: LocalDate,
    val runtime: Int?,
    val score: Float?,
    val tagline: String?,
    val title: String,
    val tmdbId: TmdbMovieId,
    val universeId: UniverseId,
    val watched: Boolean,
) {

    constructor(localMovie: LocalMovie, tmdbMovie: TmdbMovie?) : this(
        backdropPath = tmdbMovie?.backdropPath,
        budget = tmdbMovie?.budget,
        id = localMovie.id,
        overview = tmdbMovie?.overview,
        posterPath = tmdbMovie?.posterPath,
        prerequisitesIds = localMovie.prerequisitesIds,
        releaseDate = tmdbMovie?.releaseDate ?: localMovie.releaseDate,
        runtime = tmdbMovie?.runtime,
        score = tmdbMovie?.voteAverage,
        tagline = tmdbMovie?.tagline,
        title = tmdbMovie?.title ?: localMovie.originalTitle,
        tmdbId = localMovie.tmdbId,
        universeId = localMovie.universeId,
        watched = localMovie.watched,
    )

}
