package com.wizbii.cinematic.journey.presentation.util.string

import androidx.compose.runtime.Composable
import com.wizbii.cinematic.journey.domain.entity.Universe
import com.wizbii.cinematicjourney.generated.resources.Res
import com.wizbii.cinematicjourney.generated.resources.universe_name_dceu
import com.wizbii.cinematicjourney.generated.resources.universe_name_mcu
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource

internal val Universe.name: String
    @OptIn(ExperimentalResourceApi::class)
    @Composable inline get() = stringResource(
        when (id.value) {
            "dceu" -> Res.string.universe_name_dceu
            "mcu"  -> Res.string.universe_name_mcu
            else   -> error("Unknown universe $id")
        }
    )
