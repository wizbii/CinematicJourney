package com.wizbii.cinematic.journey.presentation.component.preview

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import com.wizbii.cinematic.journey.presentation.component.CinematicJourneyTheme
import com.wizbii.cinematic.journey.presentation.component.dark.mode.DarkMode
import com.wizbii.cinematic.journey.presentation.component.dark.mode.PreviewDarkModeComponent
import com.wizbii.cinematic.journey.presentation.screen.movie.MovieScreen
import com.wizbii.cinematic.journey.presentation.screen.movies.MoviesScreen
import com.wizbii.cinematic.journey.presentation.screen.settings.SettingsScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun Preview() {
    Preview(true) {
        MovieScreen()
    }
}

@Composable
private fun Preview(
    isDarkMode: Boolean = true,
    content: @Composable () -> Unit,
) {

    val darkModeComponent = PreviewDarkModeComponent(isDarkMode)
    CompositionLocalProvider(LocalPreview provides true) {
        DarkMode(darkModeComponent) {
            CinematicJourneyTheme(content = content)
        }
    }

}
