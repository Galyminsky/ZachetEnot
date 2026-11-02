package com.galyaminsky.zachetenot.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview(showBackground = true)
@Composable
private fun TypographyPreview() {
    ZachetEnotTheme {
        Column(
            modifier = Modifier
                .width(402.dp)
                .background(Background)
                .padding(
                    horizontal = Dimens.ScreenHorizontalPadding,
                    vertical = Dimens.Space24
                ),
            verticalArrangement = Arrangement.spacedBy(Dimens.Space16)
        ) {
            Text("В каком ты классе?", color = TextPrimary, style = MaterialTheme.typography.headlineLarge)
            Text("Продолжим учиться?", color = TextPrimary, style = MaterialTheme.typography.headlineMedium)
            Text("Твой следующий шаг", color = TextPrimary, style = MaterialTheme.typography.titleLarge)
            Text("Узнавай новое в удобном темпе.", color = TextPrimary, style = MaterialTheme.typography.bodyLarge)
            Text("3 класс · Русский язык", color = TextPrimary, style = MaterialTheme.typography.labelMedium)
        }
    }
}
