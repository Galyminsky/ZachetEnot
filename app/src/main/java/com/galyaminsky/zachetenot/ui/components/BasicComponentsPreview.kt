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
import com.galyaminsky.zachetenot.domain.progress.TopicStatus
import com.galyaminsky.zachetenot.ui.theme.Background
import com.galyaminsky.zachetenot.ui.theme.Dimens
import com.galyaminsky.zachetenot.ui.theme.ZachetEnotTheme

@Preview(widthDp = 402, heightDp = 720, showBackground = true)
@Composable
private fun BasicComponentsPreview() {
    ZachetEnotTheme {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Background)
                .padding(
                    horizontal = Dimens.ScreenHorizontalPadding,
                    vertical = Dimens.Space24
                ),
            verticalArrangement = Arrangement.spacedBy(Dimens.Space16)
        ) {
            AppButton(
                text = "Продолжить",
                onClick = {},
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = R.drawable.ic_arrow_right
            )
            AppButton(
                text = "Продолжить",
                onClick = {},
                modifier = Modifier.fillMaxWidth(),
                style = AppButtonStyle.Secondary
            )
            AppButton(
                text = "Пропустить",
                onClick = {},
                modifier = Modifier.fillMaxWidth(),
                style = AppButtonStyle.Text
            )
            AppButton(
                text = "Продолжить",
                onClick = {},
                modifier = Modifier.fillMaxWidth(),
                enabled = false
            )
            Row(horizontalArrangement = Arrangement.spacedBy(Dimens.Space12)) {
                StatusChip(TopicStatus.AVAILABLE)
                StatusChip(TopicStatus.PASSED)
                StatusChip(TopicStatus.LOCKED)
            }
            StepProgressBar(progress = 0.33f, modifier = Modifier.fillMaxWidth())
            StepProgressBar(progress = 1f, modifier = Modifier.fillMaxWidth())
            Row(horizontalArrangement = Arrangement.spacedBy(Dimens.Space12)) {
                SelectionIndicator(SelectionState.Default)
                SelectionIndicator(SelectionState.Selected)
                SelectionIndicator(SelectionState.Locked)
            }
        }
    }
}
