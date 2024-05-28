package com.wizbii.cinematic.journey.presentation.screen.root

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.wizbii.cinematic.journey.presentation.component.dark.mode.DarkModeComponent
import com.wizbii.cinematic.journey.presentation.screen.movie.MovieComponent
import com.wizbii.cinematic.journey.presentation.screen.movies.MoviesComponent
import com.wizbii.cinematic.journey.presentation.screen.settings.SettingsComponent
import com.wizbii.cinematic.journey.presentation.screen.universes.UniversesComponent
import kotlinx.coroutines.flow.StateFlow

interface RootComponent {

    val childStack: Value<ChildStack<*, Child>>

    val darkModeComponent: DarkModeComponent

    val initialized: StateFlow<Boolean>

    fun enhanceMoviesWithTmdbData(language: String)

    sealed class Child {
        data class MovieChild(val component: MovieComponent) : Child()
        data class MoviesChild(val component: MoviesComponent) : Child()
        data class SettingsChild(val component: SettingsComponent) : Child()
        data class UniversesChild(val component: UniversesComponent) : Child()
    }

}
