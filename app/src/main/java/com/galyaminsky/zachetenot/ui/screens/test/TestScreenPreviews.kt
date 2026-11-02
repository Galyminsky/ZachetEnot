package com.galyaminsky.zachetenot.ui.screens.test

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
private fun TestPreview(answer: AnswerUi, phase: AnswerPhase, taskLabel: String, taskText: String, actionText: String) {
    val isEmpty = phase == AnswerPhase.Empty
    val isLocked = phase == AnswerPhase.Locked
    TestScreen(
        state = TestScreenState(
            header = TestHeaderUi("3 класс · Русский язык", "Состав слова", 4, 15, if (isLocked) 4 else 3, if (isLocked) "16:38" else "16:42"),
            taskLabel = taskLabel,
            taskText = taskText,
            answer = answer,
            phase = phase,
            buttonText = if (isLocked) "Следующий вопрос" else actionText,
            caption = when {
                isLocked -> "Далее — вопрос 5 из 15"
                isEmpty -> "Сначала заполни ответ. Пропускать нельзя."
                else -> "После подтверждения ответ нельзя изменить"
            }
        ),
        onExit = {}, onAction = {}, onChoiceSelected = {}, onTextChange = {}, onPartClick = {}, onPairClick = {}
    )
}

@Preview(name = "Выбор варианта · нет ответа", widthDp = 402, heightDp = 900, showBackground = true)
@Composable
private fun ChoiceEmptyPreview() = PreviewFrame {
    TestPreview(AnswerUi.Choice(listOf("Корень", "Приставка", "Суффикс", "Окончание"), null), AnswerPhase.Empty, "Выбор варианта", "Какая часть слова «поездка» — «по-»?", "Ответить")
}

@Preview(name = "Выбор варианта · заполнен", widthDp = 402, heightDp = 900, showBackground = true)
@Composable
private fun ChoiceFilledPreview() = PreviewFrame {
    TestPreview(AnswerUi.Choice(listOf("Корень", "Приставка", "Суффикс", "Окончание"), 2), AnswerPhase.Filled, "Выбор варианта", "Какая часть слова «поездка» — «по-»?", "Ответить")
}

@Preview(name = "Выбор варианта · зафиксирован", widthDp = 402, heightDp = 900, showBackground = true)
@Composable
private fun ChoiceLockedPreview() = PreviewFrame {
    TestPreview(AnswerUi.Choice(listOf("Корень", "Приставка", "Суффикс", "Окончание"), 2), AnswerPhase.Locked, "Выбор варианта", "Какая часть слова «поездка» — «по-»?", "Ответить")
}

@Preview(name = "Ввод текста · нет ответа", widthDp = 402, heightDp = 900, showBackground = true)
@Composable
private fun TextEmptyPreview() = PreviewFrame {
    TestPreview(AnswerUi.TextInput("Корень слова", "Введи ответ", "Напиши только корень, без чёрточек.", ""), AnswerPhase.Empty, "Ввод текста", "Запиши корень слова «лесник».", "Ответить")
}

@Preview(name = "Ввод текста · заполнен", widthDp = 402, heightDp = 900, showBackground = true)
@Composable
private fun TextFilledPreview() = PreviewFrame {
    TestPreview(AnswerUi.TextInput("Корень слова", "Введи ответ", "Напиши только корень, без чёрточек.", "лес"), AnswerPhase.Filled, "Ввод текста", "Запиши корень слова «лесник».", "Ответить")
}

@Preview(name = "Ввод текста · зафиксирован", widthDp = 402, heightDp = 900, showBackground = true)
@Composable
private fun TextLockedPreview() = PreviewFrame {
    TestPreview(AnswerUi.TextInput("Корень слова", "Введи ответ", "Напиши только корень, без чёрточек.", "лес"), AnswerPhase.Locked, "Ввод текста", "Запиши корень слова «лесник».", "Ответить")
}

private val SequenceParts = listOf("ый", "н", "под", "вод")
private val SequenceFilledOrder = listOf(2, 3, 1, 0)
private val MatchingPrompts = listOf("под-", "-вод-", "-н-", "-ый")
private val MatchingAnswers = listOf("Приставка", "Корень", "Суффикс", "Окончание")

@Preview(name = "Последовательность · нет ответа", widthDp = 402, heightDp = 900, showBackground = true)
@Composable
private fun SequenceEmptyPreview() = PreviewFrame {
    TestPreview(AnswerUi.Sequence("Нажимай на части в нужном порядке", SequenceParts, emptyList(), "Выбрано 0 из 4 частей"), AnswerPhase.Empty, "Последовательность", "Собери слово «подводный» из частей по порядку.", "Проверить")
}

@Preview(name = "Последовательность · заполнен", widthDp = 402, heightDp = 900, showBackground = true)
@Composable
private fun SequenceFilledPreview() = PreviewFrame {
    TestPreview(AnswerUi.Sequence("Все 4 части расставлены", SequenceParts, SequenceFilledOrder, "Нажми на часть, чтобы убрать её и выбрать заново."), AnswerPhase.Filled, "Последовательность", "Собери слово «подводный» из частей по порядку.", "Проверить")
}

@Preview(name = "Последовательность · зафиксирован", widthDp = 402, heightDp = 960, showBackground = true)
@Composable
private fun SequenceLockedPreview() = PreviewFrame {
    TestPreview(AnswerUi.Sequence("Все 4 части расставлены", SequenceParts, SequenceFilledOrder, "Порядок сохранён."), AnswerPhase.Locked, "Последовательность", "Собери слово «подводный» из частей по порядку.", "Проверить")
}

@Preview(name = "Сопоставление · нет ответа", widthDp = 402, heightDp = 900, showBackground = true)
@Composable
private fun MatchingEmptyPreview() = PreviewFrame {
    TestPreview(AnswerUi.Matching("Для каждой части выбери её название", MatchingPrompts, listOf(null, null, null, null)), AnswerPhase.Empty, "Сопоставление", "Сопоставь части слова «подводный» с их названиями.", "Проверить")
}

@Preview(name = "Сопоставление · заполнен", widthDp = 402, heightDp = 900, showBackground = true)
@Composable
private fun MatchingFilledPreview() = PreviewFrame {
    TestPreview(AnswerUi.Matching("Все 4 пары составлены", MatchingPrompts, MatchingAnswers), AnswerPhase.Filled, "Сопоставление", "Сопоставь части слова «подводный» с их названиями.", "Проверить")
}

@Preview(name = "Сопоставление · зафиксирован", widthDp = 402, heightDp = 900, showBackground = true)
@Composable
private fun MatchingLockedPreview() = PreviewFrame {
    TestPreview(AnswerUi.Matching("Все 4 пары составлены", MatchingPrompts, MatchingAnswers), AnswerPhase.Locked, "Сопоставление", "Сопоставь части слова «подводный» с их названиями.", "Проверить")
}

@Preview(name = "Подготовка к тесту", widthDp = 402, heightDp = 900, showBackground = true)
@Composable
private fun PrepPreview() = PreviewFrame {
    PrepScreen(
        state = PrepUiState(
            contextLine = "3 класс · Русский язык",
            title = "Состав слова",
            subtitle = "Корень, приставка, суффикс и окончание.",
            conditionsTitle = "Перед началом",
            conditionsStats = "15 вопросов · 20 минут",
            conditionsRules = "Без паузы и пропуска вопросов. На каждый вопрос нужен ответ.",
            passNote = "Чтобы пройти тему, ответь верно минимум на 14 вопросов из 15.",
            warningTitle = "Пройди тест за один раз",
            warningText = "Выдели 20 минут, чтобы тебя ничто не отвлекало. Таймер нельзя остановить. После подтверждения ответ изменить нельзя.",
            buttonText = "Начать тест",
            caption = "Всё готово для нового шага"
        ),
        onBack = {}, onStart = {}
    )
}
