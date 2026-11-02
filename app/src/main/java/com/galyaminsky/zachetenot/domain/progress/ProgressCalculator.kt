package com.galyaminsky.zachetenot.domain.progress

import com.galyaminsky.zachetenot.domain.model.Topic

object ProgressCalculator {
    fun calculate(topics: List<Topic>, passedTopicIds: Set<String>): SubjectProgress {
        val orderedTopics = topics.sortedBy { it.order }
        var previousStatus: TopicStatus? = null

        val topicProgress = orderedTopics.map { topic ->
            val status = when {
                topic.id in passedTopicIds -> TopicStatus.PASSED
                previousStatus == null || previousStatus == TopicStatus.PASSED -> TopicStatus.AVAILABLE
                else -> TopicStatus.LOCKED
            }
            previousStatus = status
            TopicProgress(topic, status)
        }

        return SubjectProgress(topicProgress)
    }
}
