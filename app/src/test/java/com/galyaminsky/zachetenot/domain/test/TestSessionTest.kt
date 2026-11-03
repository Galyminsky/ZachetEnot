package com.galyaminsky.zachetenot.domain.test

import com.galyaminsky.zachetenot.domain.model.Option
import com.galyaminsky.zachetenot.domain.model.Question
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Assert.assertThrows
import org.junit.Test

class TestSessionTest {
    @Test
    fun `session rejects duplicate question IDs`() {
        val question = questionBank(1).single()

        assertThrows(IllegalArgumentException::class.java) {
            TestSession(listOf(question, question))
        }
    }

    @Test
    fun `submitAnswer returns true for correct and false for incorrect answers`() {
        val correctSession = TestSession(questionBank(1))
        assertTrue(correctSession.submitAnswer("correct-0"))

        val incorrectSession = TestSession(questionBank(1))
        assertFalse(incorrectSession.submitAnswer("wrong-0"))
    }

    @Test
    fun `submitAnswer rejects a second answer for the same question`() {
        val session = TestSession(questionBank(1))
        session.submitAnswer("correct-0")

        assertThrows(IllegalStateException::class.java) {
            session.submitAnswer("wrong-0")
        }
    }

    @Test
    fun `submitAnswer rejects an unknown option ID`() {
        val session = TestSession(questionBank(1))

        assertThrows(IllegalArgumentException::class.java) {
            session.submitAnswer("unknown")
        }
    }

    @Test
    fun `submitAnswer rejects an answer after finish`() {
        val session = TestSession(questionBank(1))
        session.finish()

        assertThrows(IllegalStateException::class.java) {
            session.submitAnswer("correct-0")
        }
    }

    @Test
    fun `moveToNext rejects moving before the current question is answered`() {
        val session = TestSession(questionBank(2))

        assertThrows(IllegalStateException::class.java) { session.moveToNext() }
    }

    @Test
    fun `moveToNext rejects moving past the last question`() {
        val session = TestSession(questionBank(1))
        session.submitAnswer("correct-0")

        assertThrows(IllegalStateException::class.java) { session.moveToNext() }
    }

    @Test
    fun `finish reports fifteen correct answers as one hundred percent and passed`() {
        val result = answerCorrectly(TestSession(questionBank(15))).finish()

        assertEquals(100, result.percent)
        assertEquals(15, result.correctCount)
        assertTrue(result.passed)
    }

    @Test
    fun `finish passes with fourteen correct answers`() {
        val session = TestSession(questionBank(15))
        answerCorrectly(session, 14)
        session.submitAnswer("wrong-14")

        assertTrue(session.finish().passed)
    }

    @Test
    fun `finish does not pass with thirteen correct answers`() {
        val session = TestSession(questionBank(15))
        answerCorrectly(session, 13)
        session.submitAnswer("wrong-13")
        session.moveToNext()
        session.submitAnswer("wrong-14")

        assertFalse(session.finish().passed)
    }

    @Test
    fun `finish counts unanswered questions as mistakes by rule`() {
        val session = TestSession(questionBank(15))
        answerCorrectly(session, 10)

        val result = session.finish()

        assertEquals(10, result.correctCount)
        assertEquals(5, result.incorrectCount)
        assertEquals(listOf("rule-1", "rule-2", "rule-0"), result.mistakesByRule.keys.toList())
        assertEquals(2, result.mistakesByRule["rule-1"])
        assertEquals(2, result.mistakesByRule["rule-2"])
        assertEquals(1, result.mistakesByRule["rule-0"])
    }

    @Test
    fun `repeated finish returns the same result`() {
        val session = TestSession(questionBank(2))
        session.submitAnswer("wrong-0")

        val firstResult = session.finish()
        val secondResult = session.finish()

        assertEquals(firstResult, secondResult)
    }

    private fun answerCorrectly(session: TestSession, count: Int = session.questions.size): TestSession {
        repeat(count) { index ->
            session.submitAnswer("correct-$index")
            if (index < count - 1) {
                session.moveToNext()
            } else if (index < session.questions.lastIndex) {
                session.moveToNext()
            }
        }
        return session
    }

    private fun questionBank(count: Int): List<Question> = (0 until count).map { index ->
        Question(
            id = "question-$index",
            topicId = "topic-1",
            ruleId = "rule-${index % 3}",
            text = "Question $index?",
            options = listOf(
                Option(id = "correct-$index", text = "Correct"),
                Option(id = "wrong-$index", text = "Wrong")
            ),
            correctOptionId = "correct-$index"
        )
    }
}
