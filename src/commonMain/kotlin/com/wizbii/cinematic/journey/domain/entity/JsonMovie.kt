package com.wizbii.cinematic.journey.domain.entity

import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

@Serializable
data class JsonMovie(
    val id: MovieId,
    val originalDate: LocalDate,
    val originalTitle: String,
    val prerequisites: Set<MovieId>,
    val tmdbId: TmdbMovieId,
)
