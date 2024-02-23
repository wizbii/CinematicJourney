package com.wizbii.cinematic.journey.presentation.screen.root

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.MutableValue
import com.wizbii.cinematic.journey.presentation.component.dark.mode.PreviewDarkModeComponent
import com.wizbii.cinematic.journey.presentation.screen.root.RootComponent.Child.UniversesChild
import com.wizbii.cinematic.journey.presentation.screen.universes.PreviewUniversesComponent
import kotlinx.coroutines.flow.MutableStateFlow

class PreviewRootComponent : RootComponent {

    override val darkModeComponent = PreviewDarkModeComponent()

    override val childStack =
        MutableValue(
            ChildStack(
                configuration = Unit,
                instance = UniversesChild(component = PreviewUniversesComponent()),
            )
        )

    override val initialized = MutableStateFlow(true)

    override fun enhanceMoviesWithTmdbData(language: String) = Unit

}
