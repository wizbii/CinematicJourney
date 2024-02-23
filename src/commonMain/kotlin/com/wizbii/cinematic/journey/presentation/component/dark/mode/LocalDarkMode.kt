package com.wizbii.cinematic.journey.presentation.component.dark.mode

import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf

val LocalDarkMode: ProvidableCompositionLocal<Boolean> =
    compositionLocalOf { error("DarkMode not found") }
