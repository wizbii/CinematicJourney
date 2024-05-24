package com.wizbii.cinematic.journey.presentation.util

import platform.UIKit.UINotificationFeedbackGenerator
import platform.UIKit.UINotificationFeedbackType.UINotificationFeedbackTypeSuccess

actual fun performHaptic() {
    UINotificationFeedbackGenerator().notificationOccurred(UINotificationFeedbackTypeSuccess)
}
