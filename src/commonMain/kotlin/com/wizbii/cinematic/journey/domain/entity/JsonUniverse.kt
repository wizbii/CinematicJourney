package com.wizbii.cinematic.journey.domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class JsonUniverse(
    val id: UniverseId,
    val isDisabled: Boolean = false,
    val isFinished: Boolean,
)
