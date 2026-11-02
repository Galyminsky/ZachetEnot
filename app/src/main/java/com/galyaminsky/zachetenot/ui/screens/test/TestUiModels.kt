package com.galyaminsky.zachetenot.ui.screens.test

enum class AnswerPhase { Empty, Filled, Locked }

data class TestHeaderUi(
    val contextLine: String,
    val title: String,
    val questionNumber: Int,
    val total: Int,
    val answered: Int,
    val timeText: String
)

sealed interface AnswerUi {
    data class Choice(val options: List<String>, val selectedIndex: Int?) : AnswerUi
    data class TextInput(val fieldLabel: String, val placeholder: String, val hint: String, val value: String) : AnswerUi
    data class Sequence(val instruction: String, val parts: List<String>, val chosenOrder: List<Int>, val footer: String) : AnswerUi
    data class Matching(val instruction: String, val prompts: List<String>, val chosen: List<String?>) : AnswerUi
}

data class TestScreenState(
    val header: TestHeaderUi,
    val taskLabel: String,
    val taskText: String,
    val answer: AnswerUi,
    val phase: AnswerPhase,
    val buttonText: String,
    val caption: String
)

data class PrepUiState(
    val contextLine: String,
    val title: String,
    val subtitle: String,
    val conditionsTitle: String,
    val conditionsStats: String,
    val conditionsRules: String,
    val passNote: String,
    val warningTitle: String,
    val warningText: String,
    val buttonText: String,
    val caption: String
)
