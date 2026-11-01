package com.galyaminsky.zachetenot.domain.model

import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test

class ContentBundleTest {
    private val topicOne = Topic(id = "topic-1", title = "Topic 1", order = 1)
    private val topicTwo = Topic(id = "topic-2", title = "Topic 2", order = 2)
    private val rule = Rule(id = "rule-1", title = "Rule", explanation = "Explanation")
    private val questionOne = question(id = "question-1", topicId = topicOne.id)
    private val questionTwo = question(id = "question-2", topicId = topicTwo.id)
    private val bundle = ContentBundle(
        topics = listOf(topicOne, topicTwo),
        rules = listOf(rule),
        questions = listOf(questionOne, questionTwo)
    )

    @Test
    fun `questionsOf returns only questions for the requested topic`() {
        assertEquals(listOf(questionOne), bundle.questionsOf(topicOne.id))
    }

    @Test
    fun `questionsOf returns an empty list for an unknown topic`() {
        assertEquals(emptyList<Question>(), bundle.questionsOf("unknown-topic"))
    }

    @Test
    fun `ruleOf returns the rule with the requested ID`() {
        assertEquals(rule, bundle.ruleOf(rule.id))
    }

    @Test
    fun `ruleOf throws for an unknown ID`() {
        assertThrows(NoSuchElementException::class.java) {
            bundle.ruleOf("unknown-rule")
        }
    }

    private fun question(id: String, topicId: String) = Question(
        id = id,
        topicId = topicId,
        ruleId = rule.id,
        text = "Question?",
        options = listOf(
            Option(id = "$id-option-a", text = "Option A"),
            Option(id = "$id-option-b", text = "Option B")
        ),
        correctOptionId = "$id-option-a"
    )
}
