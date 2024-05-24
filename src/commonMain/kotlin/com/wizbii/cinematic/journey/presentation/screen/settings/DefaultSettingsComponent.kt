package com.wizbii.cinematic.journey.presentation.screen.settings

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.childContext
import com.wizbii.cinematic.journey.domain.use.case.dark.mode.ToggleDarkModeUseCase
import com.wizbii.cinematic.journey.presentation.component.top.bar.DefaultTopBarComponent
import com.wizbii.cinematic.journey.presentation.componentCoroutineScope
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class DefaultSettingsComponent(
    ctx: ComponentContext,
    onBackButtonClicked: () -> Unit,
): SettingsComponent, KoinComponent, ComponentContext by ctx {

    private val toggleDarkModeUseCase: ToggleDarkModeUseCase by inject()

    private val scope = componentCoroutineScope()

    override val topBarComponent by lazy {
        DefaultTopBarComponent(
            ctx = childContext("top-bar"),
            displayBackButton = true,
            displaySettingsButton = false,
            onBackButtonClicked = onBackButtonClicked,
        )
    }

    override fun toggleDarkMode(isDarkMode: Boolean) {
        scope.launch {
            toggleDarkModeUseCase()
        }
    }

}
