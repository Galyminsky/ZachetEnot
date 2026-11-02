package com.galyaminsky.zachetenot.data

import android.os.SystemClock
import com.galyaminsky.zachetenot.domain.test.TimeSource

class SystemTimeSource : TimeSource {
    override fun nowMs(): Long = SystemClock.elapsedRealtime()
}
