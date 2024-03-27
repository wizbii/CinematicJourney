package com.wizbii.cinematic.journey.presentation.util

import kotlin.math.roundToLong

fun Double.toString(maxDecimals: Int = 1): String {
    require(maxDecimals >= 0)
    if (maxDecimals == 0) return roundToLong().toString()
    var multiplier = 1
    repeat(maxDecimals) { multiplier *= 10 }
    val rounded = (this * multiplier).roundToLong()
    val integerPart = rounded / multiplier
    val decimalPart = (rounded % multiplier).toDouble() / multiplier
    return (integerPart + decimalPart).toString()
}

fun Float.toString(maxDecimals: Int = 1): String =
    toDouble().toString(maxDecimals)
