package com.wizbii.cinematic.journey.presentation.screen.settings

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.wizbii.cinematic.journey.presentation.component.dark.mode.LocalDarkMode
import com.wizbii.cinematic.journey.presentation.component.scrollbar.ColumnWithScrollbar
import com.wizbii.cinematic.journey.presentation.component.top.bar.TopBarContent
import com.wizbii.cinematic.journey.whenPlatform
import com.wizbii.cinematicjourney.generated.resources.*
import com.wizbii.cinematicjourney.generated.resources.Res
import com.wizbii.cinematicjourney.generated.resources.settings_dark_mode
import com.wizbii.cinematicjourney.generated.resources.settings_light_mode
import com.wizbii.cinematicjourney.generated.resources.tmdb_attribution
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource

@Composable
fun SettingsScreen(component: SettingsComponent = PreviewSettingsComponent()) {

    val scrollBehavior = whenPlatform(
        desktop = TopAppBarDefaults.pinnedScrollBehavior(),
        mobile = TopAppBarDefaults.enterAlwaysScrollBehavior(),
    )

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            @OptIn(ExperimentalResourceApi::class)
            TopBarContent(
                component = component.topBarComponent,
                scrollBehavior = scrollBehavior,
                title = stringResource(Res.string.settings_title),
            )
        },
    ) { innerPadding ->
            Content(
                bottomSafeAreaPadding = innerPadding.calculateBottomPadding(),
                modifier = Modifier.padding(top = innerPadding.calculateTopPadding()),
                toggleDarkMode = component::toggleDarkMode
            )
    }
}

@Composable
private fun Content(
    bottomSafeAreaPadding: Dp,
    modifier: Modifier,
    toggleDarkMode: (isDarkMode: Boolean) -> Unit,
) {
    ColumnWithScrollbar(
        modifier = modifier,
        scrollBarTopPadding = 8.dp,
    ) { endPadding ->
            Column(
                modifier = Modifier
                    .padding(
                        top = 8.dp,
                        start = 16.dp,
                        end = 16.dp + endPadding,
                        bottom = 16.dp + bottomSafeAreaPadding,
                    ),
            ) {

                Settings(
                    toggleDarkMode = toggleDarkMode
                )

                HorizontalDivider(
                    modifier = Modifier.padding(
                        top = 32.dp,
                        bottom = 32.dp,
                    )
                )

                @OptIn(ExperimentalResourceApi::class)
                Text(
                    text = stringResource(Res.string.tmdb_attribution),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.tertiary,
                )

            }

    }
}

@Composable
private fun Settings(
    toggleDarkMode: (isDarkMode: Boolean) -> Unit,
) {
    DarkModeButton(
        toggleDarkMode = toggleDarkMode
    )
}

@Composable
private fun DarkModeButton(
    isDarkMode: Boolean = LocalDarkMode.current,
    toggleDarkMode: (isDarkMode: Boolean) -> Unit,
) {

    val darkModeIcon =
        if (isDarkMode) {
            Icons.Default.DarkMode
        } else {
            Icons.Default.LightMode
        }

    @OptIn(ExperimentalResourceApi::class)
    val darkModeText =
        if (isDarkMode) {
            stringResource(Res.string.settings_light_mode)
        } else {
            stringResource(Res.string.settings_dark_mode)
        }

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        @OptIn(ExperimentalResourceApi::class)
        Text(
            text = stringResource(Res.string.settings_dark_mode),
        )

        Switch(
            checked = isDarkMode,
            onCheckedChange = toggleDarkMode,
            thumbContent = {

                Icon(
                    contentDescription = darkModeText,
                    imageVector = darkModeIcon,
                    modifier = Modifier.padding(all = 4.dp)
                )
            }
        )
    }

}
