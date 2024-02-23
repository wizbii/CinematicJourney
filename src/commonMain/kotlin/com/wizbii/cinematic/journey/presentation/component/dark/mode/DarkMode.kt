package com.wizbii.cinematic.journey.presentation.component.dark.mode

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

@Composable
fun DarkMode(
    component: DarkModeComponent = PreviewDarkModeComponent(),
    content: @Composable () -> Unit,
) {

    val isDarkMode by component.isDarkMode.collectAsState()

    DarkMode(
        isDarkMode = isDarkMode,
        content = content,
    )

    LaunchedEffect(Unit) {
        component.alignPlatformDarkMode()
    }

}

@Composable
private fun DarkMode(
    isDarkMode: Boolean?,
    content: @Composable () -> Unit,
) {

    isDarkMode?.let { value ->

        CompositionLocalProvider(
            LocalDarkMode provides value,
            content = content,
        )

    }

}
