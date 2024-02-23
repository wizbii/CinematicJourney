package com.wizbii.cinematic.journey.presentation.component.top.bar

import com.arkivanov.decompose.ComponentContext
import com.wizbii.cinematic.journey.domain.use.case.dark.mode.ToggleDarkModeUseCase
import com.wizbii.cinematic.journey.presentation.componentCoroutineScope
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class DefaultTopBarComponent(
    ctx: ComponentContext,
    override val hasBackButton: Boolean,
    private val onBackButtonClicked: (() -> Unit)? = null,
) : TopBarComponent, KoinComponent, ComponentContext by ctx {

    private val toggleDarkModeUseCase: ToggleDarkModeUseCase by inject()

    private val scope = componentCoroutineScope()

    init {
        if (hasBackButton) require(onBackButtonClicked != null) {
            "Cannot have back button without handler"
        }
    }

    override fun toggleDarkMode() {
        scope.launch {
            toggleDarkModeUseCase()
        }
    }

    override fun onBackButtonClicked() {
        onBackButtonClicked?.invoke()
    }

}
