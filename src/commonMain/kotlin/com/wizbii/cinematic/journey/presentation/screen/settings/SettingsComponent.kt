package com.wizbii.cinematic.journey.presentation.screen.settings

import com.wizbii.cinematic.journey.presentation.component.top.bar.TopBarComponent

interface SettingsComponent {

    val topBarComponent: TopBarComponent

    fun toggleDarkMode(isDarkMode: Boolean): Unit

}
