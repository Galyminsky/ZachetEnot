package com.galyaminsky.zachetenot.domain.model

import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test

class QuestionTest {
    private val options = listOf(
        Option(id = "a", text = "Option A"),
        Option(id = "b", text = "Option B")
    )

    @Test
    fun `valid question is created`() {
        val question = Question(
            id = "question-1",
            topicId = "topic-1",
            ruleId = "rule-1",
            text = "Question text?",
            options = options,
            correctOptionId = "a"
        )

        assertEquals("question-1", question.id)
    }

    @Test
    fun `question with one option throws`() {
        assertThrows(IllegalArgumentException::class.java) {
            createQuestion(options = listOf(options.first()))
        }
    }

    @Test
    fun `question with duplicate option IDs throws`() {
        val duplicateIdOptions = listOf(
            Option(id = "same", text = "Option A"),
            Option(id = "same", text = "Option B")
        )

        assertThrows(IllegalArgumentException::class.java) {
            createQuestion(options = duplicateIdOptions, correctOptionId = "same")
        }
    }

    @Test
    fun `question with unknown correct option ID throws`() {
        assertThrows(IllegalArgumentException::class.java) {
            createQuestion(correctOptionId = "missing")
        }
    }

    private fun createQuestion(
        options: List<Option> = this.options,
        correctOptionId: String = "a"
    ) = Question(
        id = "question-1",
        topicId = "topic-1",
        ruleId = "rule-1",
        text = "Question text?",
        options = options,
        correctOptionId = correctOptionId
    )
}
