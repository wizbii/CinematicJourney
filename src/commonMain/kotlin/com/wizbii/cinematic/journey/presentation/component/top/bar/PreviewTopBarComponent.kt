package com.wizbii.cinematic.journey.presentation.component.top.bar

class PreviewTopBarComponent(
    override val hasBackButton: Boolean = false,
) : TopBarComponent {

    override fun toggleDarkMode() = Unit

    override fun onBackButtonClicked() = Unit

}
