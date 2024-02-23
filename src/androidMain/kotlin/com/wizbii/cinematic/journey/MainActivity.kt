package com.wizbii.cinematic.journey

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.arkivanov.decompose.defaultComponentContext
import com.wizbii.cinematic.journey.presentation.screen.root.DefaultRootComponent
import com.wizbii.cinematic.journey.presentation.screen.root.Root
import org.koin.core.component.KoinComponent

class MainActivity : ComponentActivity(), KoinComponent {

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()

        super.onCreate(savedInstanceState)

        val rootComponent = DefaultRootComponent(ctx = defaultComponentContext())

        setContent {
            Root(component = rootComponent)
        }
    }

}
