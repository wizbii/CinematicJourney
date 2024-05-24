package com.wizbii.cinematic.journey.presentation.component.top.bar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.compose.ui.text.style.TextOverflow
import com.materialkolor.ktx.blend
import com.wizbii.cinematic.journey.presentation.component.AutoSizeText
import com.wizbii.cinematicjourney.generated.resources.Res
import com.wizbii.cinematicjourney.generated.resources.settings_title
import com.wizbii.cinematicjourney.generated.resources.topbar_back
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarContent(
    component: TopBarComponent = PreviewTopBarComponent(),
    scrollBehavior: TopAppBarScrollBehavior? = null,
    title: String? = null,
) {

    val colors =
        if (scrollBehavior?.isPinned == true) {
            val containerColor = MaterialTheme.colorScheme.primaryContainer
            val contentColor = MaterialTheme.colorScheme.onPrimaryContainer
            val scrolledContainerColor = containerColor.blend(
                to = MaterialTheme.colorScheme.primary,
                amount = .1f,
            )
            TopAppBarDefaults.topAppBarColors(
                actionIconContentColor = contentColor,
                containerColor = containerColor,
                navigationIconContentColor = contentColor,
                scrolledContainerColor = scrolledContainerColor,
                titleContentColor = contentColor,
            )
        } else {
            TopAppBarDefaults.topAppBarColors(
                scrolledContainerColor = MaterialTheme.colorScheme.primaryContainer,
            )
        }

    TopAppBar(
        actions = { if (component.hasSettingsButton) Actions(navigateToSettings = component::navigateToSettings) },
        colors = colors,
        navigationIcon = { if (component.hasBackButton) BackButton(component::onBackButtonClicked) },
        scrollBehavior = scrollBehavior,
        title = {
            title?.let { Title(it) }
        },
    )

}

@Composable
private fun BackButton(
    onClick: () -> Unit,
) {

    IconButton(
        onClick = onClick,
    ) {

        @OptIn(ExperimentalResourceApi::class)
        Icon(
            contentDescription = stringResource(Res.string.topbar_back),
            imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
        )

    }

}

@Composable
private fun Title(
    text: String,
) {

    AutoSizeText(
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        style = MaterialTheme.typography.titleLarge.copy(fontWeight = SemiBold),
        text = text,
    )

}

@Composable
private fun Actions(
    navigateToSettings: () -> Unit,
) {

    val settingsIcon = Icons.Default.Settings

    @OptIn(ExperimentalResourceApi::class)
    val settingsText = stringResource(Res.string.settings_title)

    IconButton(
        onClick = navigateToSettings,
    ) {

        Icon(
            contentDescription = settingsText,
            imageVector = settingsIcon,
        )

    }

}
