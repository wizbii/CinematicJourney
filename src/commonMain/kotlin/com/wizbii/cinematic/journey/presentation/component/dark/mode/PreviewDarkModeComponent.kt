package com.wizbii.cinematic.journey.presentation.component.dark.mode

import kotlinx.coroutines.flow.MutableStateFlow

class PreviewDarkModeComponent(
    isDarkMode: Boolean = false,
) : DarkModeComponent {

    override val isDarkMode = MutableStateFlow(isDarkMode)

    override fun alignPlatformDarkMode() = Unit

}
