package com.galyaminsky.zachetenot.domain.test

/** Provides monotonic time in milliseconds. */
interface TimeSource {
    fun nowMs(): Long
}
