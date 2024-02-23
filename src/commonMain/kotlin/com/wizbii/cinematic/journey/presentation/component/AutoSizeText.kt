package com.wizbii.cinematic.journey.presentation.component

import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import com.wizbii.cinematic.journey.presentation.component.preview.LocalPreview
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.round

@Composable
fun AutoSizeText(
    maxLines: Int = Int.MAX_VALUE,
    maxScale: Double = 1.0,
    minScale: Double = 0.8,
    modifier: Modifier = Modifier,
    overflow: TextOverflow = TextOverflow.Clip,
    style: TextStyle = LocalTextStyle.current,
    text: String,
    textAlign: TextAlign? = null,
) {

    val isPreview = LocalPreview.current

    val maxFontSize = floor(style.fontSize.value * maxScale).sp
    val minFontSize = ceil(style.fontSize.value * minScale).sp

    var currentFontSize by remember { mutableStateOf(maxFontSize) }
    var isReadyToDraw by remember { mutableStateOf(false) }

    val lineHeight =
        round(style.lineHeight.value * (currentFontSize.value / style.fontSize.value)).sp

    val textStyle = style.copy(
        lineHeight = lineHeight,
        fontSize = currentFontSize,
    )

    Text(
        maxLines = maxLines,
        modifier = modifier.drawWithContent {
            if (isReadyToDraw) drawContent()
        },
        onTextLayout = { result ->
            if (!isPreview && result.hasVisualOverflow && currentFontSize > minFontSize) {
                currentFontSize = (currentFontSize.value - 1f).sp
            } else {
                isReadyToDraw = true
            }
        },
        overflow = overflow,
        style = if (isPreview) style else textStyle,
        text = text,
        textAlign = textAlign,
    )

}
