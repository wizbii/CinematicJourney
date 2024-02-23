package com.wizbii.cinematic.journey.presentation.screen.universes

import com.wizbii.cinematic.journey.domain.entity.Universe
import com.wizbii.cinematic.journey.domain.entity.UniverseId
import com.wizbii.cinematic.journey.presentation.component.top.bar.PreviewTopBarComponent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.datetime.LocalDate

class PreviewUniversesComponent : UniversesComponent {

    override val topBarComponent = PreviewTopBarComponent(false)

    override val universes = MutableStateFlow(
        listOf(
            Universe(
                id = UniverseId("dceu"),
                startDate = LocalDate.parse("2013-06-19"),
                endDate = LocalDate.parse("2023-12-20"),
            ),
            Universe(
                id = UniverseId("mcu"),
                startDate = LocalDate.parse("2008-04-30"),
                endDate = null,
            ),
        )
    )

    override fun onUniverseClick(id: UniverseId) = Unit

}
