package com.galyaminsky.zachetenot.domain.progress

import com.galyaminsky.zachetenot.domain.model.Topic
import org.junit.Assert.assertEquals
import org.junit.Test

class ProgressCalculatorTest {
    private val topics = listOf(
        Topic(id = "topic-1", title = "Topic 1", order = 1),
        Topic(id = "topic-2", title = "Topic 2", order = 2),
        Topic(id = "topic-3", title = "Topic 3", order = 3)
    )

    @Test
    fun `no passed topics makes only the first topic available`() {
        val result = ProgressCalculator.calculate(topics, emptySet())

        assertEquals(
            listOf(TopicStatus.AVAILABLE, TopicStatus.LOCKED, TopicStatus.LOCKED),
            result.topics.map { it.status }
        )
    }

    @Test
    fun `passing the first topic unlocks the second only`() {
        val result = ProgressCalculator.calculate(topics, setOf("topic-1"))

        assertEquals(
            listOf(TopicStatus.PASSED, TopicStatus.AVAILABLE, TopicStatus.LOCKED),
            result.topics.map { it.status }
        )
    }

    @Test
    fun `a passed third topic remains passed while the second is available`() {
        val result = ProgressCalculator.calculate(topics, setOf("topic-1", "topic-3"))

        assertEquals(
            listOf(TopicStatus.PASSED, TopicStatus.AVAILABLE, TopicStatus.PASSED),
            result.topics.map { it.status }
        )
    }

    @Test
    fun `all passed topics produce one hundred percent`() {
        val result = ProgressCalculator.calculate(topics, topics.map { it.id }.toSet())

        assertEquals(100, result.percent)
    }

    @Test
    fun `one passed topic out of three produces thirty-three percent`() {
        val result = ProgressCalculator.calculate(topics, setOf("topic-1"))

        assertEquals(33, result.percent)
        assertEquals(1, result.passedCount)
        assertEquals(3, result.totalCount)
    }

    @Test
    fun `unknown passed topic IDs are ignored`() {
        val result = ProgressCalculator.calculate(topics, setOf("unknown"))

        assertEquals(0, result.passedCount)
        assertEquals(TopicStatus.AVAILABLE, result.topics.first().status)
    }

    @Test
    fun `topics are sorted by order`() {
        val unorderedTopics = listOf(topics[2], topics[0], topics[1])

        val result = ProgressCalculator.calculate(unorderedTopics, emptySet())

        assertEquals(listOf("topic-1", "topic-2", "topic-3"), result.topics.map { it.topic.id })
    }

    @Test
    fun `empty topic list has zero percent`() {
        val result = ProgressCalculator.calculate(emptyList(), emptySet())

        assertEquals(0, result.percent)
        assertEquals(0, result.totalCount)
    }
}
