package com.wizbii.cinematic.journey.presentation.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import coil3.request.crossfade

@Composable
fun RemoteImage(
    contentDescription: String?,
    contentScale: ContentScale = ContentScale.Fit,
    modifier: Modifier = Modifier.fillMaxSize(),
    url: String?,
) {

    Box(
        modifier = modifier,
    ) {

        if (url != null) {

            val request = ImageRequest
                .Builder(LocalPlatformContext.current)
                .data(url)
                .crossfade(true)
                .build()

            AsyncImage(
                contentDescription = contentDescription,
                contentScale = contentScale,
                modifier = Modifier.matchParentSize(),
                model = request,
            )

        }

    }

}
