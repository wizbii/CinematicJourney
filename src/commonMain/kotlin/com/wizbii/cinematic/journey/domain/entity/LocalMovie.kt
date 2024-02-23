package com.wizbii.cinematic.journey.domain.entity

import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

@Serializable
data class LocalMovie(
    val id: MovieId,
    val originalTitle: String,
    val prerequisitesIds: Set<MovieId>,
    val releaseDate: LocalDate,
    val tmdbId: TmdbMovieId,
    val universeId: UniverseId,
    val watched: Boolean,
)
