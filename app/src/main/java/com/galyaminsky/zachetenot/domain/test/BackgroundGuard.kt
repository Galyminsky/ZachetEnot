package com.galyaminsky.zachetenot.domain.test

import com.galyaminsky.zachetenot.domain.model.TestConfig

class BackgroundGuard(
    private val timeSource: TimeSource,
    private val maxAbsenceMs: Long = TestConfig.BACKGROUND_RESET_SECONDS * 1000L
) {
    private var backgroundAtMs: Long? = null

    fun onBackground() {
        backgroundAtMs = timeSource.nowMs()
    }

    fun onForeground(): Boolean {
        val backgroundAt = backgroundAtMs ?: return false
        backgroundAtMs = null
        return timeSource.nowMs() - backgroundAt > maxAbsenceMs
    }
}
