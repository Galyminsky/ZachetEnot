package com.galyaminsky.zachetenot.domain.model

data class TestResult(
    val totalCount: Int,
    val correctCount: Int,
    val mistakesByRule: Map<String, Int>
) {
    val incorrectCount: Int
        get() = totalCount - correctCount

    val percent: Int
        get() = correctCount * 100 / totalCount

    val passed: Boolean
        get() = correctCount * 100 >= TestConfig.PASS_THRESHOLD_PERCENT * totalCount

    init {
        require(totalCount > 0) { "Total count must be greater than zero" }
        require(correctCount in 0..totalCount) { "Correct count must be within the total count" }
    }
}
