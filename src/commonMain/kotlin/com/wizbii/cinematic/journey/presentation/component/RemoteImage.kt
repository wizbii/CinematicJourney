package com.wizbii.cinematic.journey.presentation.component

import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource

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
            KamelImage(
                animationSpec = tween(),
                contentDescription = contentDescription,
                contentScale = contentScale,
                modifier = Modifier.matchParentSize(),
                resource = asyncPainterResource(url),
            )
        }
    }

}
