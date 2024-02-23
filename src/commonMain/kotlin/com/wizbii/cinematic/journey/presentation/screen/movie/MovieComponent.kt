package com.wizbii.cinematic.journey.presentation.screen.movie

import com.wizbii.cinematic.journey.domain.entity.Movie
import com.wizbii.cinematic.journey.domain.entity.MovieId
import com.wizbii.cinematic.journey.presentation.component.top.bar.TopBarComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface MovieComponent {

    val movie: StateFlow<Movie?>

    val prerequisites: StateFlow<List<Movie>?>

    val topBarComponent: TopBarComponent

    fun backdropUrl(backdropPath: String?, width: Int): StateFlow<String?>

    fun onMovieClicked(movieId: MovieId)

    fun posterUrl(posterPath: String?, width: Int): Flow<String?>

    fun setMovieWatched(movieId: MovieId, watched: Boolean)

}
