package com.wizbii.cinematic.journey.presentation.component.scrollbar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp

@Composable
actual fun LazyColumnWithScrollbar(
    contentPadding: PaddingValues,
    modifier: Modifier,
    scrollBarVerticalPadding: Dp,
    scrollBarBottomPadding: Dp,
    scrollBarTopPadding: Dp,
    state: LazyListState,
    verticalArrangement: Arrangement.Vertical,
    content: LazyListScope.() -> Unit,
) {

    LazyColumn(
        contentPadding = contentPadding,
        modifier = modifier,
        state = state,
        verticalArrangement = verticalArrangement,
        content = content,
    )

}
