package com.wizbii.cinematic.journey.presentation.component.scrollbar

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
expect fun ColumnWithScrollbar(
    modifier: Modifier = Modifier,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    scrollBarVerticalPadding: Dp = 0.dp,
    scrollBarBottomPadding: Dp = scrollBarVerticalPadding,
    scrollBarTopPadding: Dp = scrollBarVerticalPadding,
    scrollState: ScrollState = rememberScrollState(),
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    content: @Composable ColumnScope.(additionalEndPadding: Dp) -> Unit,
)
