package com.wizbii.cinematic.journey.presentation.util

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

fun PaddingValues(
    all: Dp = 0.dp,
    horizontal: Dp = all,
    vertical: Dp = all,
    start: Dp = horizontal,
    top: Dp = vertical,
    end: Dp = horizontal,
    bottom: Dp = vertical,
): PaddingValues = PaddingValues(
    start = start,
    top = top,
    end = end,
    bottom = bottom,
)
