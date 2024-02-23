package com.wizbii.cinematic.journey.presentation.screen.movies

import com.wizbii.cinematic.journey.domain.entity.Movie
import com.wizbii.cinematic.journey.domain.entity.MovieId
import com.wizbii.cinematic.journey.domain.entity.Universe
import com.wizbii.cinematic.journey.presentation.component.top.bar.TopBarComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface MoviesComponent {

    val movies: StateFlow<List<Movie>?>

    val topBarComponent: TopBarComponent

    val universe: StateFlow<Universe?>

    fun onMovieClicked(movieId: MovieId)

    fun posterUrl(posterPath: String?, width: Int): Flow<String?>

    fun setMovieWatched(movieId: MovieId, watched: Boolean)

}
