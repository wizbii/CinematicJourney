package com.wizbii.cinematic.journey.presentation.screen.settings

import com.wizbii.cinematic.journey.presentation.component.top.bar.PreviewTopBarComponent

class PreviewSettingsComponent : SettingsComponent {

    override val topBarComponent = PreviewTopBarComponent(true)

    override fun toggleDarkMode(isDarkMode: Boolean) = Unit

}
