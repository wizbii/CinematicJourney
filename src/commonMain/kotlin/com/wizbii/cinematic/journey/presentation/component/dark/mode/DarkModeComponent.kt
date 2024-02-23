package com.wizbii.cinematic.journey.presentation.component.dark.mode

import kotlinx.coroutines.flow.StateFlow

interface DarkModeComponent {

    val isDarkMode: StateFlow<Boolean?>

    fun alignPlatformDarkMode()

}
