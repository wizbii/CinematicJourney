package com.wizbii.cinematic.journey.domain.entity

import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable
import kotlin.time.Duration.Companion.days

@Serializable
data class TmdbMovie(
    val backdropPath: String?,
    val id: TmdbMovieId,
    val overview: String?,
    val posterPath: String?,
    val releaseDate: LocalDate,
    val runtime: Int?,
    val tagline: String?,
    val title: String?,
    val voteAverage: Float?,
) {

    companion object {

        val cacheDuration = 7.days

    }

}
