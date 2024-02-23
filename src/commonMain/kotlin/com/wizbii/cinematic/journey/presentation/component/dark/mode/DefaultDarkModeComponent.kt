package com.wizbii.cinematic.journey.presentation.component.dark.mode

import com.arkivanov.decompose.ComponentContext
import com.wizbii.cinematic.journey.domain.use.case.dark.mode.AlignPlatformDarkModeUseCase
import com.wizbii.cinematic.journey.domain.use.case.dark.mode.GetDarkModePreferenceUseCase
import com.wizbii.cinematic.journey.domain.use.case.dark.mode.ObserveDarkModePreferenceUseCase
import com.wizbii.cinematic.journey.domain.use.case.dark.mode.SetDarkModePreferenceUseCase
import com.wizbii.cinematic.journey.presentation.componentCoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class DefaultDarkModeComponent(
    ctx: ComponentContext,
) : DarkModeComponent, KoinComponent, ComponentContext by ctx {

    private val alignPlatformDarkModeUseCase: AlignPlatformDarkModeUseCase by inject()
    private val getDarkModePreferenceUseCase: GetDarkModePreferenceUseCase by inject()
    private val observeDarkModePreferenceUseCase: ObserveDarkModePreferenceUseCase by inject()
    private val setDarkModePreferenceUseCase: SetDarkModePreferenceUseCase by inject()

    private val scope = componentCoroutineScope()

    override val isDarkMode = MutableStateFlow<Boolean?>(null)

    init {
        scope.launch {
            val isDarkModePreference = getDarkModePreferenceUseCase()
            if (isDarkModePreference == null) {
                setDarkModePreferenceUseCase(false)
            }
            observeDarkModePreferenceUseCase().collect {
                isDarkMode.value = it
            }
        }
    }

    override fun alignPlatformDarkMode() {
        scope.launch {
            alignPlatformDarkModeUseCase()
        }
    }

}
