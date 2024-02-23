package com.wizbii.cinematic.journey.domain.entity

import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

@Serializable
data class Universe(
    val id: UniverseId,
    val startDate: LocalDate,
    val endDate: LocalDate?,
)
