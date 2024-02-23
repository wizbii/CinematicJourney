package com.wizbii.cinematic.journey.presentation.component.scrollbar

import androidx.compose.foundation.LocalScrollbarStyle
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.materialkolor.ktx.blend

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

    Box(modifier = modifier) {

        Column(
            content = { content(4.dp) },
            horizontalAlignment = horizontalAlignment,
            modifier = Modifier.verticalScroll(scrollState),
            verticalArrangement = verticalArrangement,
        )

        VerticalScrollbar(
            adapter = rememberScrollbarAdapter(scrollState),
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .fillMaxHeight()
                .padding(
                    top = scrollBarTopPadding,
                    end = 6.dp,
                    bottom = scrollBarBottomPadding,
                ),
            style = LocalScrollbarStyle.current.copy(
                hoverColor = MaterialTheme.colorScheme.primaryContainer.blend(
                    to = MaterialTheme.colorScheme.primary,
                    amount = .25f,
                ),
                shape = RoundedCornerShape(3.dp),
                unhoverColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = .5f),
            ),
        )

    }

}
