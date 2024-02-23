package com.wizbii.cinematic.journey.domain.entity

import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

@JvmInline
@Serializable
value class MovieId(val value: String) {

    override fun toString() = value

}
