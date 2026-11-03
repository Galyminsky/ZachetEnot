package com.galyaminsky.zachetenot.ui.screens.result

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.galyaminsky.zachetenot.ui.components.MistakeUi
import com.galyaminsky.zachetenot.ui.screens.test.PreviewFrame

@Preview(name = "Результат · успешно", widthDp = 402, heightDp = 931, showBackground = true)
@Composable
private fun ResultPassedPreview() {
    PreviewFrame {
        Box(Modifier.fillMaxSize()) {
            ResultScreen(
                state = ResultUiState(
                    contextLine = "3 класс · Русский язык",
                    headerLabel = "Результат",
                    title = "Тест завершён",
                    subtitle = "Состав слова",
                    passed = true,
                    verdictTitle = "Отличный шаг!",
                    verdictNote = null,
                    correctText = "14 из 15",
                    percentText = "93%",
                    correctLabel = "Правильных ответов",
                    completion = 1f,
                    completionText = "Выполнено 15 из 15 вопросов · 100%",
                    errorsValue = "1",
                    errorsLabel = "Ошибка",
                    timeValue = "12:36",
                    timeLabel = "Затрачено времени",
                    message = "Тема пройдена! Разбери одну ошибку, чтобы закрепить знания.",
                    reviewButton = "Разобрать ошибки",
                    retryButton = "Повторить тест",
                    listButton = "К списку тем"
                ),
                onReview = {}, onRetry = {}, onList = {}
            )
        }
    }
}

@Preview(name = "Результат · неуспешно", widthDp = 402, heightDp = 950, showBackground = true)
@Composable
private fun ResultFailedPreview() {
    PreviewFrame {
        Box(Modifier.fillMaxSize()) {
            ResultScreen(
                state = ResultUiState(
                    contextLine = "3 класс · Русский язык",
                    headerLabel = "Результат",
                    title = "Тест завершён",
                    subtitle = "Состав слова",
                    passed = false,
                    failedNote = "12 из 15 · Для успеха нужно 14 правильных ответов.",
                    verdictTitle = "Попробуй ещё раз",
                    verdictNote = "Пока не пройдено",
                    correctText = "12 из 15",
                    percentText = "80%",
                    correctLabel = "Правильных ответов",
                    completion = 1f,
                    completionText = "Выполнено 15 из 15 вопросов · 100%",
                    errorsValue = "3",
                    errorsLabel = "Ошибки",
                    timeValue = "12:36",
                    timeLabel = "Затрачено времени",
                    message = "Для прохождения нужно 14 из 15. Разбери 3 ошибки и попробуй снова — у тебя получится!",
                    reviewButton = "Разобрать ошибки",
                    retryButton = "Повторить тест",
                    listButton = "К списку тем"
                ),
                onReview = {}, onRetry = {}, onList = {}
            )
        }
    }
}

@Preview(name = "Разбор · одна ошибка", widthDp = 402, heightDp = 946, showBackground = true)
@Composable
private fun ReviewOneMistakePreview() {
    PreviewFrame {
        Box(Modifier.fillMaxSize()) {
            ReviewScreen(
                state = ReviewUiState(
                    contextLine = "3 класс · Русский язык",
                    title = "Разбор ошибок",
                    subtitle = "Состав слова",
                    statsLine = "1 ошибка · 14 из 15 верно · 93%",
                    timeLine = "Затрачено времени · 12:36",
                    mistakes = listOf(
                        MistakeUi(
                            questionLabel = "Вопрос 4 из 15",
                            questionText = "Какая часть слова «поездка» — «по-»?",
                            yourAnswerLabel = "Твой ответ",
                            yourAnswer = "Суффикс",
                            correctLabel = "Правильный ответ",
                            correctAnswer = "Приставка",
                            ruleTitle = "Вспомним правило",
                            ruleText = "Приставка стоит перед корнем и помогает образовать новое слово. В слове «поездка» «по-» стоит перед корнем «-езд-». Суффикс стоит после корня."
                        )
                    ),
                    footerMessage = "Ошибки помогают учиться. Теперь ты знаешь, как отличить приставку от суффикса.",
                    success = null,
                    buttonText = "К списку тем"
                ),
                onBack = {}, onList = {}
            )
        }
    }
}

@Preview(name = "Разбор · без ошибок", widthDp = 402, heightDp = 900, showBackground = true)
@Composable
private fun ReviewNoMistakesPreview() {
    PreviewFrame {
        Box(Modifier.fillMaxSize()) {
            ReviewScreen(
                state = ReviewUiState(
                    contextLine = "3 класс · Русский язык",
                    title = "Разбор ошибок",
                    subtitle = "Состав слова",
                    statsLine = "0 ошибок · 15 из 15 верно · 100%",
                    timeLine = "Затрачено времени · 12:36",
                    mistakes = emptyList(),
                    footerMessage = null,
                    success = ReviewSuccessUi(
                        title = "Ни одной ошибки!",
                        text = "Все 15 ответов верные. Ты отлично разбираешься в составе слова: находишь корень, приставку, суффикс и окончание.",
                        note = "Разбирать нечего — можно переходить к следующей теме."
                    ),
                    buttonText = "К списку тем"
                ),
                onBack = {}, onList = {}
            )
        }
    }
}
