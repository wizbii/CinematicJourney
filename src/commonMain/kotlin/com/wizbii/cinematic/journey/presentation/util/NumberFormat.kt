package com.wizbii.cinematic.journey.presentation.util

import kotlin.math.roundToLong

fun Number.formatHumanReadable(): String {
    val suffix = arrayOf("", "k", "M", "G")

    var suffixIndex = 0
    var value = toDouble()

    while (value >= 1000 && suffixIndex < suffix.lastIndex) {
        value /= 1000
        suffixIndex++
    }

    val numberAsString =
        if (value.toString(1).last() == '0') {
            value.roundToLong().toString()
        } else {
            value.toString()
                .split('.')
                .let { (i, d) -> "$i.${d.take(3)}" }
        }

    return "$numberAsString ${suffix[suffixIndex]}"
}

fun Number.toString(maxDecimals: Int = 1): String {

    require(maxDecimals >= 0)

    if (maxDecimals == 0) {
        return toDouble().roundToLong().toString()
    }

    val rawAsString = toString()
    val (rawInteger, rawDecimals) = rawAsString.split('.')

    if (rawDecimals.length <= maxDecimals) {
        return rawAsString + CharArray(maxDecimals - rawDecimals.length) { '0' }.concatToString()
    }

    var integerDigitsCount = rawInteger.length
    var allDigits = (rawInteger + rawDecimals).map(Char::digitToInt).toIntArray()
    var lastDecimalDigitIndex = rawInteger.length + maxDecimals - 1
    val firstDiscardedDecimalDigit = allDigits[lastDecimalDigitIndex + 1]
    if (firstDiscardedDecimalDigit >= 5) {
        var currentIndex = lastDecimalDigitIndex
        while (currentIndex >= 0 && allDigits[currentIndex] == 9) {
            allDigits[currentIndex--] = 0
        }
        if (currentIndex == -1) {
            allDigits = intArrayOf(1) + allDigits
            integerDigitsCount++
            lastDecimalDigitIndex++
        } else {
            allDigits[currentIndex]++
        }
    }

    return buildString {
        allDigits
            .take(integerDigitsCount)
            .forEach(::append)
        append('.')
        allDigits
            .slice(integerDigitsCount..lastDecimalDigitIndex)
            .forEach(::append)
    }

}
