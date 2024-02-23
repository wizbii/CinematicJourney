package com.wizbii.cinematic.journey.presentation.component.scrollbar

import androidx.compose.foundation.LocalScrollbarStyle
import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.materialkolor.ktx.blend

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

    Box(
        modifier = modifier,
    ) {

        LazyColumn(
            content = content,
            contentPadding = PaddingValues(
                start = contentPadding.calculateLeftPadding(LocalLayoutDirection.current),
                top = contentPadding.calculateTopPadding(),
                bottom = contentPadding.calculateBottomPadding(),
            ),
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    end = contentPadding.calculateEndPadding(LocalLayoutDirection.current) + 8.dp,
                ),
            state = state,
            verticalArrangement = verticalArrangement,
        )

        VerticalScrollbar(
            adapter = rememberScrollbarAdapter(state),
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .fillMaxHeight()
                .padding(
                    top = contentPadding.calculateTopPadding() + scrollBarTopPadding,
                    end = 8.dp,
                    bottom = contentPadding.calculateBottomPadding() + scrollBarBottomPadding,
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
