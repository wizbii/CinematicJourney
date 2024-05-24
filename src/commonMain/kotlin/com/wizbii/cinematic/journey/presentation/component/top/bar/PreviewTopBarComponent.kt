package com.wizbii.cinematic.journey.presentation.component.top.bar

class PreviewTopBarComponent(
    override val hasBackButton: Boolean = false,
    override val hasSettingsButton: Boolean = true,
) : TopBarComponent {

    override fun navigateToSettings() = Unit

    override fun onBackButtonClicked() = Unit

}
