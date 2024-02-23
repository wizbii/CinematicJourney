package com.wizbii.cinematic.journey

import kotlinx.cinterop.autoreleasepool
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.toCStringArray
import platform.Foundation.NSStringFromClass
import platform.UIKit.UIApplicationMain

@Suppress("unused")
fun main(args: List<String>) {
    memScoped {
        autoreleasepool {
            UIApplicationMain(
                argc = args.size + 1,
                argv = arrayOf("konan", *args.toTypedArray()).toCStringArray(memScope),
                principalClassName = null,
                delegateClassName = NSStringFromClass(AppDelegate)
            )
        }
    }
}
