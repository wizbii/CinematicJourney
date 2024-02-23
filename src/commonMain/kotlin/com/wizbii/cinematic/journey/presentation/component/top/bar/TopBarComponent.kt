package com.wizbii.cinematic.journey.presentation.component.top.bar

interface TopBarComponent {

    val hasBackButton: Boolean

    fun toggleDarkMode()

    fun onBackButtonClicked()

}
