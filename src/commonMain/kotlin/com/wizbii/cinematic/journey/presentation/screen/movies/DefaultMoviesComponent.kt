package com.wizbii.cinematic.journey.presentation.screen.movies

import androidx.compose.ui.text.intl.Locale
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.childContext
import com.wizbii.cinematic.journey.domain.entity.Movie
import com.wizbii.cinematic.journey.domain.entity.MovieId
import com.wizbii.cinematic.journey.domain.entity.Universe
import com.wizbii.cinematic.journey.domain.entity.UniverseId
import com.wizbii.cinematic.journey.domain.use.case.GetMoviePosterUrlForWidthUseCase
import com.wizbii.cinematic.journey.domain.use.case.ObserveMoviesForUniverseUseCase
import com.wizbii.cinematic.journey.domain.use.case.ObserveUniverseUseCase
import com.wizbii.cinematic.journey.domain.use.case.SetMovieWatchedUseCase
import com.wizbii.cinematic.journey.presentation.component.top.bar.DefaultTopBarComponent
import com.wizbii.cinematic.journey.presentation.componentCoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class DefaultMoviesComponent(
    ctx: ComponentContext,
    onBackButtonClicked: () -> Unit,
    private val onMovieSelected: (MovieId) -> Unit,
    universeId: UniverseId,
) : MoviesComponent, KoinComponent, ComponentContext by ctx {

    private val getMoviePosterUrlForWidthUseCase: GetMoviePosterUrlForWidthUseCase by inject()
    private val observeMoviesForUniverseUseCase: ObserveMoviesForUniverseUseCase by inject()
    private val observeUniverseUseCase: ObserveUniverseUseCase by inject()
    private val setMovieWatchedUseCase: SetMovieWatchedUseCase by inject()

    private val scope = componentCoroutineScope()

    private val posters = MutableStateFlow<Map<String, String>>(emptyMap())

    private val postersMutex = Mutex()

    override val movies = MutableStateFlow<List<Movie>?>(null)

    override val universe = MutableStateFlow<Universe?>(null)

    override val topBarComponent by lazy {
        DefaultTopBarComponent(
            ctx = childContext("top-bar"),
            displayBackButton = true,
            onBackButtonClicked = onBackButtonClicked,
        )
    }

    init {
        val language = Locale.current.toLanguageTag()
        scope.launch {
            observeMoviesForUniverseUseCase(universeId, language).collect { movies.value = it }
        }
        scope.launch {
            observeUniverseUseCase(universeId).collect { universe.value = it }
        }
    }

    override fun onMovieClicked(movieId: MovieId) =
        onMovieSelected(movieId)

    override fun posterUrl(posterPath: String?, width: Int): Flow<String?> {
        if (posterPath == null) return flowOf(null)
        if (posterPath !in posters.value) {
            scope.launch {
                val posterUrl = getMoviePosterUrlForWidthUseCase(posterPath, width)
                postersMutex.withLock {
                    posters.value += posterPath to posterUrl
                }
            }
        }
        return posters.map { it[posterPath] }
    }

    override fun setMovieWatched(movieId: MovieId, watched: Boolean) {
        scope.launch {
            setMovieWatchedUseCase(movieId, watched)
        }
    }

}
