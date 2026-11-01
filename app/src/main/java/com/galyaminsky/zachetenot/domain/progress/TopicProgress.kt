package com.galyaminsky.zachetenot.domain.progress

import com.galyaminsky.zachetenot.domain.model.Topic

enum class TopicStatus { LOCKED, AVAILABLE, PASSED }

data class TopicProgress(val topic: Topic, val status: TopicStatus)

data class SubjectProgress(val topics: List<TopicProgress>) {
    val totalCount: Int
        get() = topics.size

    val passedCount: Int
        get() = topics.count { it.status == TopicStatus.PASSED }

    val percent: Int
        get() = if (totalCount == 0) 0 else passedCount * 100 / totalCount
}
