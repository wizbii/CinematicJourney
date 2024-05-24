package com.wizbii.cinematic.journey.presentation.screen.movie

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.StarHalf
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.outlined.Theaters
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilledIconToggleButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight.Companion.Light
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.coerceAtMost
import androidx.compose.ui.unit.dp
import com.wizbii.cinematic.journey.domain.entity.Movie
import com.wizbii.cinematic.journey.domain.entity.MovieId
import com.wizbii.cinematic.journey.presentation.component.AutoSizeText
import com.wizbii.cinematic.journey.presentation.component.MovieListItem
import com.wizbii.cinematic.journey.presentation.component.RemoteImage
import com.wizbii.cinematic.journey.presentation.component.scrollbar.ColumnWithScrollbar
import com.wizbii.cinematic.journey.presentation.component.top.bar.TopBarContent
import com.wizbii.cinematic.journey.presentation.util.toString
import com.wizbii.cinematic.journey.whenPlatform
import com.wizbii.cinematicjourney.generated.resources.Res
import com.wizbii.cinematicjourney.generated.resources.movie_prerequisites_one
import com.wizbii.cinematicjourney.generated.resources.movie_prerequisites_other
import com.wizbii.cinematicjourney.generated.resources.movie_prerequisites_zero
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import kotlin.math.ceil

@Composable
fun MovieScreen(component: MovieComponent = PreviewMovieComponent()) {

    val movie by component.movie.collectAsState()
    val prerequisites by component.prerequisites.collectAsState()

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
                title = movie?.title ?: "…",
            )
        },
    ) { innerPadding ->

        Content(
            backdropUrlBuilder = component::backdropUrl,
            bottomSafeAreaPadding = innerPadding.calculateBottomPadding(),
            modifier = Modifier.padding(top = innerPadding.calculateTopPadding()),
            movie = movie,
            prerequisites = prerequisites,
            onMovieSelected = component::onMovieClicked,
            posterUrlBuilder = component::posterUrl,
            setMovieWatched = component::setMovieWatched,
        )

    }

}

@Composable
private fun Content(
    backdropUrlBuilder: (String?, Int) -> StateFlow<String?>,
    bottomSafeAreaPadding: Dp,
    modifier: Modifier,
    movie: Movie?,
    prerequisites: List<Movie>?,
    onMovieSelected: (MovieId) -> Unit,
    posterUrlBuilder: (String?, Int) -> Flow<String?>,
    setMovieWatched: (MovieId, Boolean) -> Unit,
) {

    if (movie == null) return

    ColumnWithScrollbar(
        modifier = modifier,
        scrollBarTopPadding = 8.dp,
        verticalArrangement = Arrangement.spacedBy(24.dp),
    ) { endPadding ->

        MovieHeader(
            backdropUrlBuilder = backdropUrlBuilder,
            additionalEndPadding = endPadding,
            movie = movie,
            posterUrlBuilder = posterUrlBuilder,
            setMovieWatched = setMovieWatched,
        )

        MovieDescription(
            modifier = Modifier.padding(
                start = 16.dp,
                end = 16.dp + endPadding,
            ),
            movie = movie,
        )

        MoviePrerequisites(
            modifier = Modifier.padding(
                start = 16.dp,
                end = 16.dp + endPadding,
                bottom = 16.dp + bottomSafeAreaPadding,
            ),
            onMovieSelected = onMovieSelected,
            prerequisiteMovies = prerequisites ?: emptyList(),
            posterUrlBuilder = posterUrlBuilder,
            setMovieWatched = setMovieWatched,
        )

    }

}

@Composable
private fun MovieHeader(
    additionalEndPadding: Dp,
    backdropUrlBuilder: (String?, Int) -> StateFlow<String?>,
    modifier: Modifier = Modifier,
    movie: Movie,
    posterUrlBuilder: (String?, Int) -> Flow<String?>,
    setMovieWatched: (MovieId, Boolean) -> Unit,
) {

    val backdropTargetAspectRatio = 16 / 9f
    val posterHeight = 180.dp
    val posterAspectRatio = 2 / 3f
    val posterWidth = posterHeight * posterAspectRatio
    val posterLeftPadding = 20.dp
    val posterNegativeTopPadding = -posterHeight * 2 / 5f

    BoxWithConstraints(
        modifier = modifier,
    ) {

        val backdropWidth = maxWidth
        val backdropHeight by derivedStateOf {
            (maxWidth / backdropTargetAspectRatio).coerceAtMost(320.dp)
        }

        Column {

            Box(modifier = Modifier.wrapContentSize()) {

                Backdrop(
                    height = backdropHeight,
                    movie = movie,
                    urlBuilder = backdropUrlBuilder,
                    width = backdropWidth,
                )

                FilledIconToggleButton(
                    checked = movie.watched,
                    modifier = Modifier
                        .alpha(.85f)
                        .align(Alignment.BottomEnd)
                        .padding(
                            end = 16.dp + additionalEndPadding,
                            bottom = 16.dp,
                        ),
                    onCheckedChange = { setMovieWatched(movie.id, !movie.watched) },
                ) {

                    Icon(
                        contentDescription = null, // TODO
                        imageVector = if (movie.watched) Icons.Default.Check else Icons.Default.Visibility,
                    )

                }

            }

            TitleColumn(
                modifier = Modifier
                    .height(posterHeight + posterNegativeTopPadding)
                    .padding(
                        top = 12.dp,
                        start = posterLeftPadding + posterWidth + 12.dp,
                        end = 16.dp + additionalEndPadding,
                        bottom = 12.dp,
                    ),
                movie = movie,
            )

        }

        Poster(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(
                    start = posterLeftPadding,
                    top = backdropHeight + posterNegativeTopPadding,
                ),
            movie = movie,
            posterHeight = posterHeight,
            posterWidth = posterWidth,
            urlBuilder = posterUrlBuilder,
        )

    }

}

@Composable
private fun MovieDescription(
    modifier: Modifier = Modifier,
    movie: Movie?,
) {

    if (movie?.tagline == null && movie?.overview == null) return

    val tagline by derivedStateOf { movie.tagline ?: "" }
    val overview by derivedStateOf {
        movie.overview?.replace("([.?!])\\s+".toRegex(), "$1\n") ?: ""
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {

        Text(
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = SemiBold),
            text = tagline,
        )

        Text(
            style = MaterialTheme.typography.bodyMedium,
            text = overview,
        )

    }

}

@Composable
private fun MoviePrerequisites(
    modifier: Modifier = Modifier,
    onMovieSelected: (MovieId) -> Unit,
    prerequisiteMovies: List<Movie>,
    posterUrlBuilder: (String?, Int) -> Flow<String?>,
    setMovieWatched: (MovieId, Boolean) -> Unit,
) {

    // TODO Replace with plurals once implemented in resources library
    @OptIn(ExperimentalResourceApi::class)
    val prerequisitesTitleText = when (prerequisiteMovies.size) {
        0    -> stringResource(Res.string.movie_prerequisites_zero)
        1    -> stringResource(Res.string.movie_prerequisites_one)
        else -> stringResource(Res.string.movie_prerequisites_other)
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {

        Text(
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = SemiBold),
            text = prerequisitesTitleText,
        )

        prerequisiteMovies.forEach { movie ->

            MovieListItem(
                moviePosterPath = movie.posterPath,
                movieReleaseDate = movie.releaseDate,
                movieTitle = movie.title,
                movieWatched = movie.watched,
                onSelected = { onMovieSelected(movie.id) },
                posterHeight = 90.dp,
                posterUrlBuilder = posterUrlBuilder,
                setMovieWatched = { setMovieWatched(movie.id, it) },
            )

        }

    }

}

@Composable
private fun Backdrop(
    height: Dp,
    movie: Movie,
    urlBuilder: (String?, Int) -> StateFlow<String?>,
    width: Dp,
) {

    val backdropWidthPx = with(LocalDensity.current) { ceil(width.toPx()).toInt() }
    val backdropUrl by urlBuilder(movie.backdropPath, backdropWidthPx).collectAsState()

    ElevatedCard(
        elevation = CardDefaults.elevatedCardElevation(4.dp),
        modifier = Modifier
            .width(width)
            .height(height),
        shape = RectangleShape,
    ) {
        RemoteImage(
            contentDescription = null, // TODO
            contentScale = ContentScale.FillWidth,
            url = backdropUrl,
        )
    }

}

@Composable
private fun Poster(
    modifier: Modifier = Modifier,
    movie: Movie,
    posterHeight: Dp,
    posterWidth: Dp,
    urlBuilder: (String?, Int) -> Flow<String?>,
) {

    val posterWidthPx = with(LocalDensity.current) { ceil(posterWidth.toPx()).toInt() }
    val posterUrl by urlBuilder(movie.posterPath, posterWidthPx).collectAsState(null)

    ElevatedCard(
        elevation = CardDefaults.elevatedCardElevation(16.dp),
        modifier = modifier.wrapContentSize(),
        shape = RoundedCornerShape(8.dp),
    ) {

        RemoteImage(
            contentDescription = null, // TODO
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.size(posterWidth, posterHeight),
            url = posterUrl,
        )

    }

}

@Composable
private fun TitleColumn(
    modifier: Modifier = Modifier,
    movie: Movie,
) {

    val runtime = remember(movie.runtime) {
        movie.runtime?.let { minutes ->
            "${minutes / 60}h${minutes.rem(60).toString().padStart(2, '0')}"
        }
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterVertically),
    ) {

        AutoSizeText(
            maxLines = 3,
            maxScale = 1.2,
            minScale = .6,
            modifier = Modifier.fillMaxWidth().weight(1f, false),
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = SemiBold),
            text = movie.title,
            textAlign = TextAlign.Center,
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
            modifier = Modifier.fillMaxWidth(),
        ) {

            Chip(
                icon = Icons.Outlined.Theaters,
                text = movie.releaseDate.year.toString(),
            )

            runtime?.let { runtime ->

                Chip(
                    icon = Icons.Outlined.Timer,
                    text = runtime,
                )

            }

            movie.score?.let { score ->

                if (score > 0) {
                    val icon = when (score) {
                        in 0.0..2.0 -> Icons.Outlined.Star
                        in 2.0..8.0 -> Icons.AutoMirrored.Outlined.StarHalf
                        else -> Icons.Filled.Star
                    }

                    Chip(
                        icon = icon,
                        text = score.toString(1),
                    )
                }

            }

        }

    }

}

@Composable
private fun Chip(
    icon: ImageVector,
    text: String,
) {

    val iconSize = with(LocalDensity.current) {
        MaterialTheme.typography.bodyMedium.lineHeight.times(.9).toDp()
    }

    Row(
        horizontalArrangement = Arrangement.spacedBy(2.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {

        Icon(
            contentDescription = null,
            imageVector = icon,
            modifier = Modifier.size(iconSize),
        )

        Text(
            maxLines = 1,
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = Light),
            text = text,
        )

    }

}
