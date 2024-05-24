package com.wizbii.cinematic.journey.presentation.screen.movies

import com.wizbii.cinematic.journey.domain.entity.Movie
import com.wizbii.cinematic.journey.domain.entity.MovieId
import com.wizbii.cinematic.journey.domain.entity.TmdbMovieId
import com.wizbii.cinematic.journey.domain.entity.Universe
import com.wizbii.cinematic.journey.domain.entity.UniverseId
import com.wizbii.cinematic.journey.presentation.component.top.bar.PreviewTopBarComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.datetime.LocalDate
import kotlin.random.Random

class PreviewMoviesComponent : MoviesComponent {

    override val movies = MutableStateFlow(
        List(10) { index ->
            Movie(
                backdropPath = null,
                budget = 1000,
                id = MovieId(index.toString()),
                overview = null,
                posterPath = null,
                prerequisitesIds = emptySet(),
                releaseDate = LocalDate.parse("2008-04-30"),
                runtime = -1,
                score = null,
                tagline = null,
                title = "Iron Man",
                tmdbId = TmdbMovieId(-1),
                universeId = UniverseId("mcu"),
                watched = Random(index).nextBoolean(),
            )
        }
    )

    override val universe = MutableStateFlow(
        Universe(
            id = UniverseId("mcu"),
            startDate = LocalDate.parse("2008-04-30"),
            endDate = null,
        )
    )

    override val topBarComponent = PreviewTopBarComponent(true)

    override fun onMovieClicked(movieId: MovieId) = Unit

    override fun posterUrl(posterPath: String?, width: Int): Flow<String?> = flowOf(null)

    override fun setMovieWatched(movieId: MovieId, watched: Boolean) = Unit

}
