package com.galyaminsky.zachetenot.domain.test

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertThrows
import org.junit.Assert.assertTrue
import org.junit.Test

class FakeTimeSource(initialTimeMs: Long = 0L) : TimeSource {
    var currentTimeMs: Long = initialTimeMs

    override fun nowMs(): Long = currentTimeMs

    fun advanceBy(durationMs: Long) {
        currentTimeMs += durationMs
    }
}

class TestTimerTest {
    @Test
    fun `timer starts with twenty minutes remaining`() {
        val timer = TestTimer(FakeTimeSource())

        timer.start()

        assertEquals(20 * 60_000L, timer.remainingMs())
    }

    @Test
    fun `remaining time decreases as time passes`() {
        val timeSource = FakeTimeSource()
        val timer = TestTimer(timeSource)
        timer.start()

        timeSource.advanceBy(5 * 60_000L)

        assertEquals(15 * 60_000L, timer.remainingMs())
    }

    @Test
    fun `timer expires at twenty minutes and remaining time is clamped to zero`() {
        val timeSource = FakeTimeSource()
        val timer = TestTimer(timeSource)
        timer.start()

        timeSource.advanceBy(20 * 60_000L)
        assertTrue(timer.isExpired())
        assertEquals(0L, timer.remainingMs())

        timeSource.advanceBy(5 * 60_000L)
        assertEquals(0L, timer.remainingMs())
    }

    @Test
    fun `deadline does not emit a late warning`() {
        val timeSource = FakeTimeSource()
        val timer = TestTimer(timeSource)
        timer.start()
        timeSource.advanceBy(20 * 60_000L - 1L)
        assertFalse(timer.isExpired())
        timeSource.advanceBy(1L)

        assertTrue(timer.isExpired())
        assertNull(timer.consumeWarning())
        assertNull(timer.consumeWarning())
    }

    @Test
    fun `five-minute warning is not emitted one second early`() {
        val timeSource = FakeTimeSource()
        val timer = TestTimer(timeSource)
        timer.start()
        timeSource.advanceBy(15 * 60_000L - 1_000L)

        assertNull(timer.consumeWarning())
    }

    @Test
    fun `five-minute and one-minute warnings are each emitted once`() {
        val timeSource = FakeTimeSource()
        val timer = TestTimer(timeSource)
        timer.start()
        timeSource.advanceBy(15 * 60_000L)

        assertEquals(5, timer.consumeWarning())
        assertNull(timer.consumeWarning())

        timeSource.advanceBy(4 * 60_000L - 1_000L)
        assertNull(timer.consumeWarning())
        timeSource.advanceBy(1_000L)

        assertEquals(1, timer.consumeWarning())
        assertNull(timer.consumeWarning())
    }

    @Test
    fun `large time jump emits only the lowest reached warning`() {
        val timeSource = FakeTimeSource()
        val timer = TestTimer(timeSource)
        timer.start()
        timeSource.advanceBy(10 * 60_000L)
        timeSource.advanceBy(9 * 60_000L + 30_000L)

        assertEquals(1, timer.consumeWarning())
        assertNull(timer.consumeWarning())
    }

    @Test
    fun `remaining time and warnings require the timer to be started`() {
        val timer = TestTimer(FakeTimeSource())

        assertThrows(IllegalStateException::class.java) { timer.remainingMs() }
        assertThrows(IllegalStateException::class.java) { timer.consumeWarning() }
    }

    @Test
    fun `timer cannot be started twice`() {
        val timer = TestTimer(FakeTimeSource())
        timer.start()

        assertThrows(IllegalStateException::class.java) { timer.start() }
    }
}
