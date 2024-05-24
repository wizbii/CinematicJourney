package com.wizbii.cinematic.journey.presentation.util

import android.content.Context
import android.view.HapticFeedbackConstants
import android.view.View
import android.view.accessibility.AccessibilityManager
import com.wizbii.cinematic.journey.mainActivity

actual fun performHaptic() {
    val view = mainActivity?.findViewById<View>(android.R.id.content) ?: return
    if (view.context.isTouchExplorationEnabled()) return
    view.isHapticFeedbackEnabled = true
    view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
}

private fun Context.isTouchExplorationEnabled(): Boolean {
    val accessibilityManager = getSystemService(Context.ACCESSIBILITY_SERVICE) as AccessibilityManager?
    return accessibilityManager?.isTouchExplorationEnabled ?: false
}
