package com.wizbii.cinematic.journey.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilledTonalIconToggleButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.wizbii.cinematic.journey.presentation.util.string.localizedName
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate

private const val posterAspectRatio = 2 / 3f

@Composable
fun MovieListItem(
    modifier: Modifier = Modifier,
    moviePosterPath: String?,
    movieReleaseDate: LocalDate,
    movieTitle: String,
    movieWatched: Boolean,
    onSelected: () -> Unit,
    posterHeight: Dp = 120.dp,
    posterUrlBuilder: (String?, Int) -> Flow<String?>,
    setMovieWatched: (Boolean) -> Unit,
) {

    val posterWidth = posterHeight * posterAspectRatio
    val posterWidthPx = with(LocalDensity.current) { posterWidth.roundToPx() }
    val posterUrl by posterUrlBuilder(moviePosterPath, posterWidthPx).collectAsState(null)

    val movieReleaseDateText =
        "${movieReleaseDate.month.localizedName} ${movieReleaseDate.year}"

    MovieListItem(
        modifier = modifier,
        movieReleaseDateText = movieReleaseDateText,
        movieTitle = movieTitle,
        movieWatched = movieWatched,
        onClick = onSelected,
        posterHeight = posterHeight,
        posterUrl = posterUrl,
        setMovieWatched = setMovieWatched,
    )

}

@Composable
private fun MovieListItem(
    modifier: Modifier = Modifier,
    movieReleaseDateText: String,
    movieTitle: String,
    movieWatched: Boolean,
    onClick: () -> Unit,
    posterHeight: Dp,
    posterUrl: String?,
    setMovieWatched: (Boolean) -> Unit,
) {

    ElevatedCard(
        elevation = CardDefaults.elevatedCardElevation(8.dp),
        modifier = modifier.fillMaxWidth(),
        onClick = onClick,
        shape = RoundedCornerShape(4.dp),
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {

            RemoteImage(
                contentDescription = null, // TODO
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .height(posterHeight)
                    .aspectRatio(posterAspectRatio)
                    .background(MaterialTheme.colorScheme.surfaceColorAtElevation(16.dp)),
                url = posterUrl,
            )

            Column(
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {

                AutoSizeText(
                    maxLines = 2,
                    minScale = .9,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                    text = movieTitle,
                )

                Text(
                    style = MaterialTheme.typography.bodyMedium,
                    text = movieReleaseDateText,
                )

            }

            FilledTonalIconToggleButton(
                checked = movieWatched,
                modifier = Modifier.padding(horizontal = 12.dp),
                onCheckedChange = { setMovieWatched(!movieWatched) }
            ) {

                Icon(
                    contentDescription = null, // TODO
                    imageVector = if (movieWatched) Icons.Default.Check else Icons.Default.Visibility,
                )

            }

        }

    }

}
