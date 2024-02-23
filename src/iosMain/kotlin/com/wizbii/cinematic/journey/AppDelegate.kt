@file:Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")

package com.wizbii.cinematic.journey

import androidx.compose.ui.window.ComposeUIViewController
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.ApplicationLifecycle
import com.wizbii.cinematic.journey.core.koinAppDeclaration
import com.wizbii.cinematic.journey.presentation.screen.root.DefaultRootComponent
import com.wizbii.cinematic.journey.presentation.screen.root.Root
import org.koin.core.context.startKoin
import platform.UIKit.UIApplication
import platform.UIKit.UIApplicationDelegateProtocol
import platform.UIKit.UIApplicationDelegateProtocolMeta
import platform.UIKit.UIColor
import platform.UIKit.UIScreen
import platform.UIKit.UIWindow
import platform.darwin.NSObject
import platform.darwin.NSObjectMeta

class AppDelegate @OverrideInit constructor() : NSObject(), UIApplicationDelegateProtocol {

    companion object : NSObjectMeta(), UIApplicationDelegateProtocolMeta

    private val rootComponentContext: ComponentContext =
        DefaultComponentContext(ApplicationLifecycle())

    private var uiWindow: UIWindow? = null

    override fun application(
        application: UIApplication,
        didFinishLaunchingWithOptions: Map<Any?, *>?,
    ): Boolean {
        startKoin(koinAppDeclaration)
        configureWindowWithStartComponent()
        return true
    }

    override fun setWindow(window: UIWindow?) {
        uiWindow = window
    }

    override fun window(): UIWindow? = uiWindow

    private fun configureWindowWithStartComponent() {
        setWindow(UIWindow(frame = UIScreen.mainScreen.bounds))
        window!!.run {
            backgroundColor = UIColor.blackColor
            rootViewController = ComposeUIViewController {
                Root(DefaultRootComponent(rootComponentContext))
            }
            makeKeyAndVisible()
        }
    }

}
