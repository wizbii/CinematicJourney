package com.wizbii.cinematic.journey.domain.use.case.dark.mode

import androidx.core.view.WindowCompat
import com.wizbii.cinematic.journey.mainActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

actual suspend fun setPlatformDarkMode(isDarkMode: Boolean) =
    withContext(Dispatchers.Main) {
        val window = mainActivity?.window ?: return@withContext
        WindowCompat.getInsetsController(window, window.decorView).isAppearanceLightStatusBars = !isDarkMode
    }
