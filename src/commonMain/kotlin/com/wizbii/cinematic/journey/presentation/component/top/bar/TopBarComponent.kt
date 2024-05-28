package com.wizbii.cinematic.journey.presentation.component.top.bar

interface TopBarComponent {

    val hasBackButton: Boolean

    val hasSettingsButton: Boolean

    fun navigateToSettings()

    fun onBackButtonClicked()

}
