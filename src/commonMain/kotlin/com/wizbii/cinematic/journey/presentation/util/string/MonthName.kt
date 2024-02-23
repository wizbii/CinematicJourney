package com.wizbii.cinematic.journey.presentation.util.string

import androidx.compose.runtime.Composable
import com.wizbii.cinematicjourney.generated.resources.Res
import com.wizbii.cinematicjourney.generated.resources.month_01
import com.wizbii.cinematicjourney.generated.resources.month_02
import com.wizbii.cinematicjourney.generated.resources.month_03
import com.wizbii.cinematicjourney.generated.resources.month_04
import com.wizbii.cinematicjourney.generated.resources.month_05
import com.wizbii.cinematicjourney.generated.resources.month_06
import com.wizbii.cinematicjourney.generated.resources.month_07
import com.wizbii.cinematicjourney.generated.resources.month_08
import com.wizbii.cinematicjourney.generated.resources.month_09
import com.wizbii.cinematicjourney.generated.resources.month_10
import com.wizbii.cinematicjourney.generated.resources.month_11
import com.wizbii.cinematicjourney.generated.resources.month_12
import kotlinx.datetime.Month
import kotlinx.datetime.number
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource

internal val Month.localizedName: String
    @OptIn(ExperimentalResourceApi::class)
    @Composable inline get() = stringResource(
        when (number) {
            1    -> Res.string.month_01
            2    -> Res.string.month_02
            3    -> Res.string.month_03
            4    -> Res.string.month_04
            5    -> Res.string.month_05
            6    -> Res.string.month_06
            7    -> Res.string.month_07
            8    -> Res.string.month_08
            9    -> Res.string.month_09
            10   -> Res.string.month_10
            11   -> Res.string.month_11
            12   -> Res.string.month_12
            else -> error("Invalid month number $number")
        }
    )
