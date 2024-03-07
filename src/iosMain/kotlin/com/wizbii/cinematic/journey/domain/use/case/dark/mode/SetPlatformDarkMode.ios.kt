package com.wizbii.cinematic.journey.domain.use.case.dark.mode

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import platform.UIKit.UIApplication
import platform.UIKit.UIStatusBarStyleDarkContent
import platform.UIKit.UIStatusBarStyleLightContent
import platform.UIKit.setStatusBarStyle

actual suspend fun setPlatformDarkMode(isDarkMode: Boolean) {
    withContext(Dispatchers.Main) {
        val statusBarStyle = if (isDarkMode) UIStatusBarStyleLightContent else UIStatusBarStyleDarkContent
        UIApplication.sharedApplication.setStatusBarStyle(statusBarStyle, true)
    }
}
