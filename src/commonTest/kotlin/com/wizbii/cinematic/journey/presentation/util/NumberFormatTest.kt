package com.wizbii.cinematic.journey.presentation.util

import kotlin.math.E
import kotlin.math.PI
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class NumberFormatTest {

    @Test
    fun `Double formatting should fail with negative maxDecimals`() {
        assertFailsWith<IllegalArgumentException> {
            .0.toString(-1)
        }
        assertFailsWith<IllegalArgumentException> {
            .0.toString(-1337)
        }
        assertFailsWith<IllegalArgumentException> {
            .0.toString(Int.MIN_VALUE)
        }
    }

    @Test
    fun `Float formatting should fail with negative maxDecimals`() {
        assertFailsWith<IllegalArgumentException> {
            0f.toString(-1)
        }
        assertFailsWith<IllegalArgumentException> {
            0f.toString(-1337)
        }
        assertFailsWith<IllegalArgumentException> {
            0f.toString(Int.MIN_VALUE)
        }
    }

    @Test
    fun `Double formatting should round to integer when maxDecimals == 0`() {
        assertEquals("0", .0.toString(0))
        assertEquals("0", .1337.toString(0))
        assertEquals("1", .9.toString(0))
        assertEquals("13", 13.37.toString(0))
    }

    @Test
    fun `Float formatting should round to integer when maxDecimals == 0`() {
        assertEquals("0", 0f.toString(0))
        assertEquals("0", .1337f.toString(0))
        assertEquals("1", .9f.toString(0))
        assertEquals("13", 13.37f.toString(0))
    }

    @Test
    fun `Double formatting should round when maxDecimals is smaller than the number of decimals`() {
        assertEquals("2.72", E.toString(2))
        assertEquals("3.14", PI.toString(2))
    }

    @Test
    fun `Float formatting should round when maxDecimals is smaller than the number of decimals`() {
        assertEquals("0.33", (1 / 3f).toString(2))
        assertEquals("0.67", (2 / 3f).toString(2))
    }

    @Test
    fun `Double formatting should display enough decimals even when maxDecimals is larger than the number of decimals`() {
        assertEquals("1.2000000000", 1.2.toString(10))
        assertEquals("1.2300000000", 1.23.toString(10))
        assertEquals("1.2340000000", 1.234.toString(10))
    }

    @Test
    fun `Float formatting should display enough decimals even when maxDecimals is larger than the number of decimals`() {
        assertEquals("1.2000000000", 1.2f.toString(10))
        assertEquals("1.2300000000", 1.23f.toString(10))
        assertEquals("1.2340000000", 1.234f.toString(10))
    }

    @Test
    fun `Rounding limit test`() {
        assertEquals("2", 1.500001.toString(0))
        assertEquals("1.0", 0.9995.toString(1))
        assertEquals("1.0", 0.9999.toString(1))
        assertEquals("1.0", 1.0499.toString(1))
        assertEquals("1.1", 1.0500.toString(1))
        assertEquals("1.000", 0.9995.toString(3))
        assertEquals("1.000", 0.9999.toString(3))
        assertEquals("1.050", 1.0499.toString(3))
        assertEquals("1.050", 1.0500.toString(3))
    }

}
