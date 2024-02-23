package com.wizbii.cinematic.journey.presentation.screen.root

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.text.intl.Locale
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.wizbii.cinematic.journey.presentation.component.CinematicJourneyTheme
import com.wizbii.cinematic.journey.presentation.component.dark.mode.DarkMode
import com.wizbii.cinematic.journey.presentation.screen.movie.MovieScreen
import com.wizbii.cinematic.journey.presentation.screen.movies.MoviesScreen
import com.wizbii.cinematic.journey.presentation.screen.root.RootComponent.Child.MovieChild
import com.wizbii.cinematic.journey.presentation.screen.root.RootComponent.Child.MoviesChild
import com.wizbii.cinematic.journey.presentation.screen.root.RootComponent.Child.UniversesChild
import com.wizbii.cinematic.journey.presentation.screen.universes.UniversesScreen

@Composable
fun Root(component: RootComponent = PreviewRootComponent()) {

    val initialized by component.initialized.collectAsState()

    DarkMode(component.darkModeComponent) {

        CinematicJourneyTheme {

            Children(
                stack = component.childStack,
                animation = stackAnimation(slide()),
            ) {
                when (val child = it.instance) {
                    is MovieChild     -> MovieScreen(child.component)
                    is MoviesChild    -> MoviesScreen(child.component)
                    is UniversesChild -> UniversesScreen(child.component)
                }
            }

        }
    }

    LaunchedEffect(initialized) {
        if (initialized) {
            component.enhanceMoviesWithTmdbData(Locale.current.toLanguageTag())
        }
    }

}
