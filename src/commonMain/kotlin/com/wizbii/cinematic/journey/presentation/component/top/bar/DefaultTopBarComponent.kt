package com.wizbii.cinematic.journey.presentation.component.top.bar

import com.arkivanov.decompose.ComponentContext
import com.wizbii.cinematic.journey.isAndroid
import org.koin.core.component.KoinComponent

class DefaultTopBarComponent(
    ctx: ComponentContext,
    private val displayBackButton: Boolean,
    private val displaySettingsButton: Boolean = true,
    private val onBackButtonClicked: (() -> Unit)? = null,
    private val onSettingsButtonClicked: (() -> Unit)? = null,
) : TopBarComponent, KoinComponent, ComponentContext by ctx {

    override val hasBackButton: Boolean
        get() = !isAndroid && displayBackButton

    override val hasSettingsButton: Boolean
        get() = displaySettingsButton

    init {
        if (displayBackButton) require(onBackButtonClicked != null) {
            "Cannot have back button without handler"
        }
    }

    override fun navigateToSettings() {
        onSettingsButtonClicked?.invoke()
    }

    override fun onBackButtonClicked() {
        onBackButtonClicked?.invoke()
    }

}
