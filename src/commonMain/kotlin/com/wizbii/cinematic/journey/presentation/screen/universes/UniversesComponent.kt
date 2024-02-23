package com.wizbii.cinematic.journey.presentation.screen.universes

import com.wizbii.cinematic.journey.domain.entity.Universe
import com.wizbii.cinematic.journey.domain.entity.UniverseId
import com.wizbii.cinematic.journey.presentation.component.top.bar.TopBarComponent
import kotlinx.coroutines.flow.StateFlow

interface UniversesComponent {

    val topBarComponent: TopBarComponent

    val universes: StateFlow<List<Universe>?>

    fun onUniverseClick(id: UniverseId)

}
