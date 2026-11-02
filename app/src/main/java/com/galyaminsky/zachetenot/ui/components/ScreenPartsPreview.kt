package com.galyaminsky.zachetenot.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.galyaminsky.zachetenot.R
import com.galyaminsky.zachetenot.domain.progress.TopicStatus
import com.galyaminsky.zachetenot.ui.theme.Background
import com.galyaminsky.zachetenot.ui.theme.Dimens
import com.galyaminsky.zachetenot.ui.theme.Primary
import com.galyaminsky.zachetenot.ui.theme.ZachetEnotTheme

@Preview(widthDp = 402, heightDp = 1250, showBackground = true)
@Composable
private fun ScreenPartsPreview() {
    ZachetEnotTheme {
        Column(
            modifier = Modifier.fillMaxWidth().background(Background).padding(Dimens.ScreenHorizontalPadding),
            verticalArrangement = Arrangement.spacedBy(Dimens.Space16)
        ) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                AppLogo()
                StageIndicator(1, 2)
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                BackButton(onClick = {})
                StageIndicator(2, 2)
            }
            InfoChip("3 класс")
            HintRow(R.drawable.ic_lock, "Пока открыт 3 класс. Другие классы появятся позже.")
            HintRow(R.drawable.ic_sparkles, "Начнём с русского языка. Остальные предметы — уже в планах!", iconTint = Primary)
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                AppLogo()
                SettingsButton(onClick = {})
            }
            ProgressCard(2, 6, 33, "Хорошее начало. Продолжай в своём темпе!")
            SectionHeader("Темы курса", "6 тем")
            TopicCard(1, "Речь и текст", "Виды речи, тема и главная мысль текста.", TopicStatus.PASSED, onClick = {})
            TopicCard(3, "Состав слова", "Корень, приставка, суффикс и окончание.", TopicStatus.AVAILABLE, onClick = {})
            TopicCard(4, "Правописание корней", "Безударные гласные и парные согласные.", TopicStatus.LOCKED, onClick = {})
        }
    }
}
