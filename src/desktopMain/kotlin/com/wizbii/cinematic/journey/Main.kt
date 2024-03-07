package com.wizbii.cinematic.journey

import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import co.touchlab.kermit.Logger
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.lifecycle.LifecycleController
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.wizbii.cinematic.journey.core.koinAppDeclaration
import com.wizbii.cinematic.journey.presentation.screen.root.DefaultRootComponent
import com.wizbii.cinematic.journey.presentation.screen.root.Root
import com.wizbii.cinematicjourney.generated.resources.Res
import com.wizbii.cinematicjourney.generated.resources.app_name
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import org.koin.core.context.startKoin
import java.awt.Dimension

private const val DEFAULT_WIDTH = 360
private const val DEFAULT_HEIGHT = 600

@OptIn(ExperimentalResourceApi::class)
fun main() = runBlocking {

    Logger.setLogWriters(Slf4jLogWriter())

    startKoin(koinAppDeclaration)

    val lifecycle = LifecycleRegistry()
    val ctx = DefaultComponentContext(lifecycle)
    val rootComponent = withContext(Dispatchers.Main) {
        DefaultRootComponent(ctx)
    }

    application {

        val windowState =
            rememberWindowState(
                size = DpSize(DEFAULT_WIDTH.dp, DEFAULT_HEIGHT.dp),
            )

        @OptIn(ExperimentalDecomposeApi::class)
        LifecycleController(lifecycle, windowState)

        Window(
            onCloseRequest = ::exitApplication,
            state = windowState,
            title = stringResource(Res.string.app_name),
        ) {
            window.minimumSize = Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT)
            Root(component = rootComponent)
        }

    }

}
