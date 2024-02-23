package com.wizbii.cinematic.journey.domain.entity

import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

@JvmInline
@Serializable
value class TmdbMovieId(val value: Int) {

    override fun toString() = value.toString()

}
