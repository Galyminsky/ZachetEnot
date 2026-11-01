package com.galyaminsky.zachetenot.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.galyaminsky.zachetenot.R
import com.galyaminsky.zachetenot.ui.theme.Background
import com.galyaminsky.zachetenot.ui.theme.Dimens
import com.galyaminsky.zachetenot.ui.theme.ZachetEnotTheme

@Preview(widthDp = 402, heightDp = 900, showBackground = true)
@Composable
private fun CardsPreview() {
    ZachetEnotTheme {
        Column(
            modifier = Modifier.fillMaxWidth().background(Background).padding(Dimens.ScreenHorizontalPadding),
            verticalArrangement = Arrangement.spacedBy(Dimens.Space12)
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                ClassCard(3, SelectionState.Default, R.drawable.illustration_school, {}, Modifier.weight(1f))
                ClassCard(3, SelectionState.Selected, R.drawable.illustration_school, {}, Modifier.weight(1f))
            }
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                ClassCard(1, SelectionState.Locked, R.drawable.illustration_locked, {}, Modifier.weight(1f))
                ClassCard(2, SelectionState.Locked, R.drawable.illustration_locked_2, {}, Modifier.weight(1f))
            }
            SubjectCard("Русский язык", "Начнём с языка и слов", SelectionState.Selected, {}, iconText = "Aa")
            SubjectCard("Русский язык", "Начнём с языка и слов", SelectionState.Default, {}, iconText = "Aa")
            SubjectCard("Математика", "Скоро появится", SelectionState.Locked, {}, icon = R.drawable.ic_calculator)
        }
    }
}
