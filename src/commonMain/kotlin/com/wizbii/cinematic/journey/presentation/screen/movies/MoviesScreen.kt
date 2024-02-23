package com.wizbii.cinematic.journey.presentation.screen.movies

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.wizbii.cinematic.journey.domain.entity.Movie
import com.wizbii.cinematic.journey.domain.entity.MovieId
import com.wizbii.cinematic.journey.presentation.component.MovieListItem
import com.wizbii.cinematic.journey.presentation.component.scrollbar.LazyColumnWithScrollbar
import com.wizbii.cinematic.journey.presentation.component.top.bar.TopBarContent
import com.wizbii.cinematic.journey.presentation.util.PaddingValues
import com.wizbii.cinematic.journey.presentation.util.string.name
import com.wizbii.cinematic.journey.whenPlatform
import kotlinx.coroutines.flow.Flow

@Composable
fun MoviesScreen(component: MoviesComponent = PreviewMoviesComponent()) {

    val movies by component.movies.collectAsState()
    val universe by component.universe.collectAsState()

    val scrollBehavior = whenPlatform(
        desktop = TopAppBarDefaults.pinnedScrollBehavior(),
        mobile = TopAppBarDefaults.enterAlwaysScrollBehavior(),
    )

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopBarContent(
                component = component.topBarComponent,
                scrollBehavior = scrollBehavior,
                title = universe?.name ?: "…",
            )
        },
    ) { innerPadding ->

        Content(
            bottomSafeAreaPadding = innerPadding.calculateBottomPadding(),
            modifier = Modifier.padding(top = innerPadding.calculateTopPadding()),
            movies = movies,
            onMovieSelected = component::onMovieClicked,
            posterUrlBuilder = component::posterUrl,
            setMovieWatched = component::setMovieWatched,
        )

    }

}

@Composable
private fun Content(
    bottomSafeAreaPadding: Dp,
    modifier: Modifier = Modifier,
    movies: List<Movie>?,
    onMovieSelected: (MovieId) -> Unit,
    posterUrlBuilder: (String?, Int) -> Flow<String?>,
    setMovieWatched: (MovieId, Boolean) -> Unit,
) {

    if (movies == null) return

    LazyColumnWithScrollbar(
        contentPadding = PaddingValues(
            all = 16.dp,
            bottom = 16.dp + bottomSafeAreaPadding,
        ),
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {

        items(
            items = movies,
            key = { it.id.value },
        ) { movie ->

            MovieListItem(
                modifier = Modifier.animateItemPlacement(),
                moviePosterPath = movie.posterPath,
                movieReleaseDate = movie.releaseDate,
                movieTitle = movie.title,
                movieWatched = movie.watched,
                onSelected = { onMovieSelected(movie.id) },
                posterUrlBuilder = posterUrlBuilder,
                setMovieWatched = { setMovieWatched(movie.id, it) },
            )

        }

    }

}
