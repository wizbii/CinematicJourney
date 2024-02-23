package com.wizbii.cinematic.journey

expect val isAndroid: Boolean
expect val isIos: Boolean

val isDesktop: Boolean inline get() = !isMobile
val isMobile: Boolean inline get() = isAndroid || isIos

fun <T> whenPlatform(
    desktop: T,
    mobile: T,
    android: T = mobile,
    ios: T = mobile,
): T = when {
    isAndroid -> android
    isIos     -> ios
    else      -> desktop
}
