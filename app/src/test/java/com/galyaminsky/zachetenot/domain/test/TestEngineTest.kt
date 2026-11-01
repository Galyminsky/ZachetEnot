package com.galyaminsky.zachetenot.domain.test

import com.galyaminsky.zachetenot.domain.model.Option
import com.galyaminsky.zachetenot.domain.model.Question
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test
import kotlin.random.Random

class TestEngineTest {
    @Test
    fun `startSession selects fifteen distinct questions and preserves each option set`() {
        val bank = questionBank(20)
        val originalOptionIds = bank.associate { question ->
            question.id to question.options.map { it.id }.toSet()
        }
        val session = TestEngine(Random(42)).startSession(bank)
        val selected = collectQuestions(session)

        assertEquals(15, selected.size)
        assertEquals(15, selected.map { it.id }.toSet().size)
        selected.forEach { question ->
            assertEquals(originalOptionIds.getValue(question.id), question.options.map { it.id }.toSet())
        }
    }

    @Test
    fun `startSession rejects a bank with fewer than fifteen questions`() {
        assertThrows(IllegalArgumentException::class.java) {
            TestEngine(Random(1)).startSession(questionBank(14))
        }
    }

    @Test
    fun `same random seed produces the same question order`() {
        val bank = questionBank(20)
        val firstOrder = collectQuestions(TestEngine(Random(1234)).startSession(bank)).map { it.id }
        val secondOrder = collectQuestions(TestEngine(Random(1234)).startSession(bank)).map { it.id }

        assertEquals(firstOrder, secondOrder)
    }

    private fun collectQuestions(session: TestSession): List<Question> = buildList {
        repeat(session.questions.size) { index ->
            val question = session.currentQuestion
            add(question)
            session.submitAnswer(question.correctOptionId)
            if (index < session.questions.lastIndex) {
                session.moveToNext()
            }
        }
    }

    private fun questionBank(count: Int): List<Question> = (0 until count).map { index ->
        Question(
            id = "question-$index",
            topicId = "topic-1",
            ruleId = "rule-${index % 3}",
            text = "Question $index?",
            options = listOf(
                Option(id = "correct-$index", text = "Correct"),
                Option(id = "wrong-a-$index", text = "Wrong A"),
                Option(id = "wrong-b-$index", text = "Wrong B")
            ),
            correctOptionId = "correct-$index"
        )
    }
}
