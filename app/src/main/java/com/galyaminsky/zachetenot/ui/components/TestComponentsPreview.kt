package com.galyaminsky.zachetenot.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.galyaminsky.zachetenot.ui.theme.Background
import com.galyaminsky.zachetenot.ui.theme.Dimens
import com.galyaminsky.zachetenot.ui.theme.TextSecondary
import com.galyaminsky.zachetenot.ui.theme.ZachetEnotTheme

@Preview(widthDp = 402, heightDp = 1700, showBackground = true)
@Composable
private fun TestComponentsPreview() {
    ZachetEnotTheme {
        Column(Modifier.background(Background).padding(Dimens.ScreenHorizontalPadding), verticalArrangement = Arrangement.spacedBy(24.dp)) {
            TestTopBar("16:42", {})
            TestProgressHeader("3 класс · Русский язык", "Состав слова", 4, 15, 3)
            TaskCard("Выбор варианта", "Какая часть слова «поездка» — «по-»?")
            Text(text = "Ответ заполнен", style = MaterialTheme.typography.labelMedium, color = TextSecondary)
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                ChoiceOption("Корень", false, false, {})
                ChoiceOption("Приставка", false, false, {})
                ChoiceOption("Суффикс", true, false, {})
                ChoiceOption("Окончание", false, false, {})
            }
            Text(text = "Ответ зафиксирован", style = MaterialTheme.typography.labelMedium, color = TextSecondary)
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                ChoiceOption("Корень", false, true, {})
                ChoiceOption("Приставка", false, true, {})
                ChoiceOption("Суффикс", true, true, {})
                ChoiceOption("Окончание", false, true, {})
            }
            TestActionBlock("Ответить", false, {}, "Сначала заполни ответ. Пропускать нельзя.")
            TestActionBlock("Следующий вопрос", true, {}, "Далее — вопрос 5 из 15", showLockedNotice = true)
        }
    }
}
