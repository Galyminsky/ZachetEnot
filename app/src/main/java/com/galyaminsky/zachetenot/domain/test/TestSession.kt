package com.galyaminsky.zachetenot.domain.test

import com.galyaminsky.zachetenot.domain.model.Question
import com.galyaminsky.zachetenot.domain.model.TestResult

class TestSession(val questions: List<Question>) {
    var currentIndex: Int = 0
        private set

    private val answers = linkedMapOf<String, Boolean>()
    private var completedResult: TestResult? = null

    val currentQuestion: Question
        get() = questions[currentIndex]

    val isCurrentAnswered: Boolean
        get() = answers.containsKey(currentQuestion.id)

    val isLastQuestion: Boolean
        get() = currentIndex == questions.lastIndex

    var isFinished: Boolean = false
        private set

    init {
        require(questions.isNotEmpty()) { "A test session must contain at least one question" }
        require(questions.map { it.id }.toSet().size == questions.size) {
            "Question IDs must be unique within a test session"
        }
    }

    fun submitAnswer(optionId: String): Boolean {
        check(!isFinished) { "The test session is finished" }
        check(!isCurrentAnswered) { "The current question has already been answered" }
        require(currentQuestion.options.any { it.id == optionId }) {
            "Unknown option ID: $optionId"
        }

        val isCorrect = optionId == currentQuestion.correctOptionId
        answers[currentQuestion.id] = isCorrect
        return isCorrect
    }

    fun moveToNext() {
        check(!isFinished) { "The test session is finished" }
        check(isCurrentAnswered) { "The current question must be answered first" }
        check(!isLastQuestion) { "There is no next question" }
        currentIndex++
    }

    fun finish(): TestResult {
        completedResult?.let { return it }

        val mistakesByRule = linkedMapOf<String, Int>()
        questions.forEach { question ->
            if (answers[question.id] != true) {
                mistakesByRule[question.ruleId] = (mistakesByRule[question.ruleId] ?: 0) + 1
            }
        }

        val result = TestResult(
            totalCount = questions.size,
            correctCount = answers.values.count { it },
            mistakesByRule = mistakesByRule
        )
        completedResult = result
        isFinished = true
        return result
    }
}
