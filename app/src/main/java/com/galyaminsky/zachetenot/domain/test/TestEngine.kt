package com.galyaminsky.zachetenot.domain.test

import com.galyaminsky.zachetenot.domain.model.Question
import com.galyaminsky.zachetenot.domain.model.TestConfig
import kotlin.random.Random

class TestEngine(private val random: Random = Random.Default) {
    fun startSession(bank: List<Question>): TestSession {
        require(bank.size >= TestConfig.QUESTIONS_PER_TEST) {
            "Question bank must contain at least ${TestConfig.QUESTIONS_PER_TEST} questions"
        }

        val selectedQuestions = bank.shuffled(random)
            .take(TestConfig.QUESTIONS_PER_TEST)
            .map { question -> question.copy(options = question.options.shuffled(random)) }

        return TestSession(selectedQuestions)
    }
}
