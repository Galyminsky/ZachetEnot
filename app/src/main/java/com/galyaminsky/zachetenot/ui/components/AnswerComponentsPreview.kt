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

@Preview(widthDp = 402, heightDp = 1400, showBackground = true)
@Composable
private fun AnswerComponentsPreview() {
    ZachetEnotTheme {
        Column(Modifier.background(Background).padding(Dimens.ScreenHorizontalPadding), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            GroupLabel("Ввод текста: нет ответа")
            TextAnswerField("Корень слова", "", {}, "Введи ответ", "Напиши только корень, без чёрточек.", false)
            GroupLabel("Ввод текста: заполнен")
            TextAnswerField("Корень слова", "лес", {}, "Введи ответ", "Напиши только корень, без чёрточек.", false)
            GroupLabel("Ввод текста: зафиксирован")
            TextAnswerField("Корень слова", "лес", {}, "Введи ответ", "Напиши только корень, без чёрточек.", true)

            GroupLabel("Последовательность: нет ответа")
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                SequenceItem("ый", null, false, {})
                SequenceItem("н", null, false, {})
                SequenceItem("под", null, false, {})
                SequenceItem("вод", null, false, {})
            }
            GroupLabel("Последовательность: заполнен")
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                SequenceItem("под", 1, false, {})
                SequenceItem("вод", 2, false, {})
                SequenceItem("н", 3, false, {})
                SequenceItem("ый", 4, false, {})
            }
            GroupLabel("Последовательность: зафиксирован")
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                SequenceItem("под", 1, true, {})
                SequenceItem("вод", 2, true, {})
                SequenceItem("н", 3, true, {})
                SequenceItem("ый", 4, true, {})
            }

            GroupLabel("Сопоставление: нет ответа")
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                MatchingRow("под-", null, false, {})
                MatchingRow("-вод-", null, false, {})
            }
            GroupLabel("Сопоставление: заполнен")
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                MatchingRow("под-", "Приставка", false, {})
                MatchingRow("-вод-", "Корень", false, {})
            }
            GroupLabel("Сопоставление: зафиксирован")
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                MatchingRow("под-", "Приставка", true, {})
                MatchingRow("-вод-", "Корень", true, {})
            }
        }
    }
}

@Composable
private fun GroupLabel(text: String) {
    Text(text = text, style = MaterialTheme.typography.labelMedium, color = TextSecondary)
}
