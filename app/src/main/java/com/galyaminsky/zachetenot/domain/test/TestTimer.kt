package com.galyaminsky.zachetenot.domain.test

import com.galyaminsky.zachetenot.domain.model.TestConfig
import kotlin.math.max

class TestTimer(
    private val timeSource: TimeSource,
    private val limitMs: Long = TestConfig.TIME_LIMIT_MINUTES * 60_000L
) {
    private var startedAtMs: Long? = null
    private val emittedWarnings = mutableSetOf<Int>()

    fun start() {
        check(startedAtMs == null) { "Timer has already started" }
        startedAtMs = timeSource.nowMs()
    }

    fun remainingMs(): Long {
        val startedAt = startedAtMs ?: error("Timer has not started")
        return max(0L, limitMs - (timeSource.nowMs() - startedAt))
    }

    fun isExpired(): Boolean = remainingMs() == 0L

    fun consumeWarning(): Int? {
        val remaining = remainingMs()
        if (remaining == 0L) return null
        val reachedWarnings = TestConfig.WARNING_MINUTES.filter { minutes ->
            minutes !in emittedWarnings && remaining <= minutes * 60_000L
        }
        emittedWarnings.addAll(reachedWarnings)
        return reachedWarnings.minOrNull()
    }
}
