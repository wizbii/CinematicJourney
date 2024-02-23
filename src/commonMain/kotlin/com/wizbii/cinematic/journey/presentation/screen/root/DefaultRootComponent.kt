package com.wizbii.cinematic.journey.presentation.screen.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.childContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.pushNew
import com.arkivanov.decompose.value.Value
import com.wizbii.cinematic.journey.domain.entity.MovieId
import com.wizbii.cinematic.journey.domain.entity.UniverseId
import com.wizbii.cinematic.journey.domain.use.case.start.EnhanceMoviesWithTmdbUseCase
import com.wizbii.cinematic.journey.domain.use.case.start.InitializeOrUpdateDatabaseUseCase
import com.wizbii.cinematic.journey.presentation.component.dark.mode.DefaultDarkModeComponent
import com.wizbii.cinematic.journey.presentation.componentCoroutineScope
import com.wizbii.cinematic.journey.presentation.screen.movie.DefaultMovieComponent
import com.wizbii.cinematic.journey.presentation.screen.movies.DefaultMoviesComponent
import com.wizbii.cinematic.journey.presentation.screen.root.RootComponent.Child.MovieChild
import com.wizbii.cinematic.journey.presentation.screen.root.RootComponent.Child.MoviesChild
import com.wizbii.cinematic.journey.presentation.screen.root.RootComponent.Child.UniversesChild
import com.wizbii.cinematic.journey.presentation.screen.universes.DefaultUniversesComponent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class DefaultRootComponent(
    ctx: ComponentContext,
) : RootComponent, KoinComponent, ComponentContext by ctx {

    private val enhanceMoviesWithTmdbUseCase: EnhanceMoviesWithTmdbUseCase by inject()
    private val initializeOrUpdateDatabaseUseCase: InitializeOrUpdateDatabaseUseCase by inject()

    private val navigation = StackNavigation<Config>()
    private val scope = componentCoroutineScope()

    override val darkModeComponent by lazy {
        DefaultDarkModeComponent(childContext("dark-mode"))
    }

    override val childStack: Value<ChildStack<*, RootComponent.Child>> =
        childStack(
            childFactory = ::createChild,
            handleBackButton = true,
            initialConfiguration = Config.Universes,
            serializer = Config.serializer(),
            source = navigation,
        )

    override val initialized = MutableStateFlow(false)

    init {
        scope.launch {
            initializeOrUpdateDatabaseUseCase()
            initialized.value = true
        }
    }

    override fun enhanceMoviesWithTmdbData(language: String) {
        check(initialized.value) { "Tried to enhance uninitialized database" }
        scope.launch {
            enhanceMoviesWithTmdbUseCase(language)
        }
    }

    private fun createChild(config: Config, ctx: ComponentContext): RootComponent.Child =
        when (config) {

            is Config.Movie -> MovieChild(
                DefaultMovieComponent(
                    ctx = ctx,
                    movieId = config.movieId,
                    onBackButtonClicked = navigation::pop,
                    onMovieSelected = {
                        @OptIn(ExperimentalDecomposeApi::class)
                        navigation.pushNew(Config.Movie(it))
                    },
                )
            )

            is Config.Movies -> MoviesChild(
                DefaultMoviesComponent(
                    ctx = ctx,
                    onBackButtonClicked = navigation::pop,
                    onMovieSelected = {
                        @OptIn(ExperimentalDecomposeApi::class)
                        navigation.pushNew(Config.Movie(it))
                    },
                    universeId = config.universeId,
                )
            )

            Config.Universes -> UniversesChild(
                DefaultUniversesComponent(
                    ctx = ctx,
                    onUniverseSelected = {
                        @OptIn(ExperimentalDecomposeApi::class)
                        navigation.pushNew(Config.Movies(it))
                    }
                )
            )

        }

    @Serializable
    private sealed interface Config {

        @Serializable
        data class Movie(val movieId: MovieId) : Config

        @Serializable
        data class Movies(val universeId: UniverseId) : Config

        @Serializable
        data object Universes : Config

    }

}
