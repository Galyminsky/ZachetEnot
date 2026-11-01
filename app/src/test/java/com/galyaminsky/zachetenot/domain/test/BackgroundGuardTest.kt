package com.galyaminsky.zachetenot.domain.test

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class BackgroundGuardTest {
    @Test
    fun `absence of thirty seconds does not reset the attempt`() {
        val timeSource = FakeTimeSource()
        val guard = BackgroundGuard(timeSource)
        guard.onBackground()

        timeSource.advanceBy(30_000L)

        assertFalse(guard.onForeground())
    }

    @Test
    fun `absence of sixty-one seconds resets the attempt`() {
        val timeSource = FakeTimeSource()
        val guard = BackgroundGuard(timeSource)
        guard.onBackground()

        timeSource.advanceBy(61_000L)

        assertTrue(guard.onForeground())
    }

    @Test
    fun `absence of exactly sixty seconds does not reset the attempt`() {
        val timeSource = FakeTimeSource()
        val guard = BackgroundGuard(timeSource)
        guard.onBackground()

        timeSource.advanceBy(60_000L)

        assertFalse(guard.onForeground())
    }

    @Test
    fun `foreground without a preceding background event does not reset the attempt`() {
        val guard = BackgroundGuard(FakeTimeSource())

        assertFalse(guard.onForeground())
    }

    @Test
    fun `repeated foreground event does not reset the attempt`() {
        val timeSource = FakeTimeSource()
        val guard = BackgroundGuard(timeSource)
        guard.onBackground()
        timeSource.advanceBy(61_000L)

        assertTrue(guard.onForeground())
        assertFalse(guard.onForeground())
    }
}
