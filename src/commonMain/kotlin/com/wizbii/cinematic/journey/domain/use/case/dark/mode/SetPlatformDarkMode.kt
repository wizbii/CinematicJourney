package com.wizbii.cinematic.journey.domain.use.case.dark.mode

/**
 * If the platform supports a builtin Dark Mode, sets it.
 *
 * Used by [ToggleDarkModeUseCase].
 */
expect suspend fun setPlatformDarkMode(isDarkMode: Boolean)
