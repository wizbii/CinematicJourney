package com.wizbii.cinematic.journey.presentation.screen.movie

import androidx.compose.ui.text.intl.Locale
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.childContext
import com.wizbii.cinematic.journey.domain.entity.Movie
import com.wizbii.cinematic.journey.domain.entity.MovieId
import com.wizbii.cinematic.journey.domain.use.case.GetMovieBackdropUrlForWidthUseCase
import com.wizbii.cinematic.journey.domain.use.case.GetMoviePosterUrlForWidthUseCase
import com.wizbii.cinematic.journey.domain.use.case.ObserveMovieUseCase
import com.wizbii.cinematic.journey.domain.use.case.SetMovieWatchedUseCase
import com.wizbii.cinematic.journey.presentation.component.top.bar.DefaultTopBarComponent
import com.wizbii.cinematic.journey.presentation.componentCoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class DefaultMovieComponent(
    ctx: ComponentContext,
    movieId: MovieId,
    onBackButtonClicked: () -> Unit,
    private val onMovieSelected: (MovieId) -> Unit,
) : MovieComponent, KoinComponent, ComponentContext by ctx {

    private val getMovieBackdropUrlForWidthUseCase: GetMovieBackdropUrlForWidthUseCase by inject()
    private val getMoviePosterUrlForWidthUseCase: GetMoviePosterUrlForWidthUseCase by inject()
    private val observeMovieUseCase: ObserveMovieUseCase by inject()
    private val setMovieWatchedUseCase: SetMovieWatchedUseCase by inject()

    private val scope = componentCoroutineScope()

    private val backdropUrl = MutableStateFlow<String?>(null)

    private val posters = MutableStateFlow<Map<String, String>>(emptyMap())

    private val postersMutex = Mutex()

    override val movie = MutableStateFlow<Movie?>(null)

    override val prerequisites = MutableStateFlow<List<Movie>?>(null)

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
            observeMovieUseCase(movieId, language).collect { movie.value = it }
        }
        scope.launch {
            @OptIn(ExperimentalCoroutinesApi::class)
            movie
                .filterNotNull()
                .flatMapLatest { movie ->
                    combine(
                        flows = movie.prerequisitesIds.map {
                            observeMovieUseCase(it, language)
                        },
                        transform = { prerequisiteMovies ->
                            prerequisiteMovies
                                .toList()
                                .sortedBy(Movie::releaseDate)
                        }
                    )
                }
                .collect {
                    prerequisites.value = it
                }
        }
    }

    override fun backdropUrl(backdropPath: String?, width: Int): StateFlow<String?> {
        if (backdropPath != null && backdropUrl.value == null) {
            scope.launch {
                backdropUrl.value = getMovieBackdropUrlForWidthUseCase(backdropPath, width)
            }
        }
        return backdropUrl
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
