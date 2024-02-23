package com.wizbii.cinematic.journey.presentation.screen.universes

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.childContext
import com.wizbii.cinematic.journey.domain.entity.Universe
import com.wizbii.cinematic.journey.domain.entity.UniverseId
import com.wizbii.cinematic.journey.domain.use.case.ObserveUniversesUseCase
import com.wizbii.cinematic.journey.presentation.component.top.bar.DefaultTopBarComponent
import com.wizbii.cinematic.journey.presentation.componentCoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class DefaultUniversesComponent(
    ctx: ComponentContext,
    private val onUniverseSelected: (UniverseId) -> Unit,
) : UniversesComponent, KoinComponent, ComponentContext by ctx {

    private val observeUniversesUseCase: ObserveUniversesUseCase by inject()

    private val scope = componentCoroutineScope()

    override val topBarComponent by lazy {
        DefaultTopBarComponent(
            ctx = childContext("top-bar"),
            hasBackButton = false,
        )
    }

    override val universes = MutableStateFlow<List<Universe>?>(null)

    init {
        scope.launch {
            observeUniversesUseCase().collect { universes.value = it }
        }
    }

    override fun onUniverseClick(id: UniverseId) =
        onUniverseSelected(id)

}
