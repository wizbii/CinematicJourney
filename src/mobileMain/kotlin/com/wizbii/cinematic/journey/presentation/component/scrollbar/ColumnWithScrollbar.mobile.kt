package com.wizbii.cinematic.journey.presentation.component.scrollbar

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
actual fun ColumnWithScrollbar(
    modifier: Modifier,
    horizontalAlignment: Alignment.Horizontal,
    scrollBarVerticalPadding: Dp,
    scrollBarBottomPadding: Dp,
    scrollBarTopPadding: Dp,
    scrollState: ScrollState,
    verticalArrangement: Arrangement.Vertical,
    content: @Composable ColumnScope.(additionalEndPadding: Dp) -> Unit,
) {

    Column(
        content = { content(0.dp) },
        horizontalAlignment = horizontalAlignment,
        modifier = modifier.verticalScroll(scrollState),
        verticalArrangement = verticalArrangement,
    )

}
