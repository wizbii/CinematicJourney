package com.wizbii.cinematic.journey.presentation.screen.universes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import com.wizbii.cinematic.journey.domain.entity.Universe
import com.wizbii.cinematic.journey.domain.entity.UniverseId
import com.wizbii.cinematic.journey.presentation.component.AutoSizeText
import com.wizbii.cinematic.journey.presentation.component.scrollbar.LazyColumnWithScrollbar
import com.wizbii.cinematic.journey.presentation.component.top.bar.TopBarContent
import com.wizbii.cinematic.journey.presentation.util.string.name
import com.wizbii.cinematic.journey.whenPlatform
import com.wizbii.cinematicjourney.generated.resources.Res
import com.wizbii.cinematicjourney.generated.resources.universes_ongoing
import com.wizbii.cinematicjourney.generated.resources.universes_title
import org.jetbrains.compose.resources.stringResource

@Composable
fun UniversesScreen(component: UniversesComponent = PreviewUniversesComponent()) {

    val universes by component.universes.collectAsState()

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
                title = stringResource(Res.string.universes_title),
            )
        },
    ) { innerPadding ->

        Content(
            modifier = Modifier.padding(innerPadding),
            onUniverseSelected = component::onUniverseClick,
            universes = universes,
        )

    }

}

@Composable
private fun Content(
    modifier: Modifier = Modifier,
    onUniverseSelected: (UniverseId) -> Unit,
    universes: List<Universe>?,
) {

    if (universes == null) return

    LazyColumnWithScrollbar(
        contentPadding = PaddingValues(16.dp),
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        items(
            items = universes,
            key = { it.id.value },
        ) { universe ->
            Item(
                modifier = Modifier.animateItemPlacement(),
                onUniverseSelected = onUniverseSelected,
                universe = universe,
            )
        }
    }

}

@Composable
private fun Item(
    modifier: Modifier = Modifier,
    onUniverseSelected: (UniverseId) -> Unit,
    universe: Universe,
) {

    val universeStart = universe.startDate.year.toString()

    val universeEnd = universe.endDate?.year?.toString()
        ?: stringResource(Res.string.universes_ongoing)

    val subtitle = "$universeStart - $universeEnd"

    ElevatedCard(
        elevation = CardDefaults.elevatedCardElevation(8.dp),
        modifier = modifier.fillMaxWidth(),
        onClick = { onUniverseSelected(universe.id) },
    ) {

        Column(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {

            AutoSizeText(
                maxLines = 1,
                style = MaterialTheme.typography.titleMedium,
                text = universe.name,
            )

            Text(
                style = MaterialTheme.typography.bodyMedium,
                text = subtitle,
            )

        }

    }

}
