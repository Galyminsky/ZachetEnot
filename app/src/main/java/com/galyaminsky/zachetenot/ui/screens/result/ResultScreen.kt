package com.galyaminsky.zachetenot.ui.screens.result

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.galyaminsky.zachetenot.R
import com.galyaminsky.zachetenot.ui.components.AppButton
import com.galyaminsky.zachetenot.ui.components.AppButtonStyle
import com.galyaminsky.zachetenot.ui.components.AppLogo
import com.galyaminsky.zachetenot.ui.components.ResultSummaryCard
import com.galyaminsky.zachetenot.ui.components.StatTile
import com.galyaminsky.zachetenot.ui.theme.Background
import com.galyaminsky.zachetenot.ui.theme.TextSecondary
import com.galyaminsky.zachetenot.ui.theme.TextPrimary

@Composable
fun ResultScreen(state: ResultUiState, onReview: () -> Unit, onRetry: () -> Unit, onList: () -> Unit, modifier: Modifier = Modifier, reviewAvailable: Boolean = true) {
    Column(
        modifier.fillMaxSize().background(Background).verticalScroll(rememberScrollState())
            .padding(start = 24.dp, end = 24.dp, top = 8.dp, bottom = 12.dp)
    ) {
        Row(Modifier.fillMaxWidth().height(48.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            AppLogo()
            Text(text = state.headerLabel, style = MaterialTheme.typography.labelMedium, color = TextSecondary)
        }
        Spacer(Modifier.height(24.dp))
        Text(text = state.contextLine, style = MaterialTheme.typography.labelMedium, color = TextSecondary)
        Spacer(Modifier.height(8.dp))
        Text(text = state.title, style = MaterialTheme.typography.headlineLarge, color = TextPrimary)
        Spacer(Modifier.height(8.dp))
        Text(text = state.subtitle, style = MaterialTheme.typography.bodyLarge, color = TextSecondary)
        Spacer(Modifier.height(24.dp))
        ResultSummaryCard(
            passed = state.passed,
            verdictTitle = state.verdictTitle,
            verdictNote = state.verdictNote,
            correctText = state.correctText,
            percentText = state.percentText,
            correctLabel = state.correctLabel,
            completion = state.completion,
            completionText = state.completionText
        )
        Spacer(Modifier.height(24.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            StatTile(value = state.errorsValue, label = state.errorsLabel, modifier = Modifier.weight(1f))
            StatTile(value = state.timeValue, label = state.timeLabel, modifier = Modifier.weight(1f))
        }
        Spacer(Modifier.height(24.dp))
        Text(text = state.message, style = MaterialTheme.typography.bodyLarge, color = TextSecondary)
        Spacer(Modifier.height(24.dp))
        if (reviewAvailable) {
            AppButton(text = state.reviewButton, onClick = onReview, modifier = Modifier.fillMaxWidth(), style = AppButtonStyle.Primary, trailingIcon = R.drawable.ic_arrow_right)
            Spacer(Modifier.height(12.dp))
        }
        AppButton(text = state.retryButton, onClick = onRetry, modifier = Modifier.fillMaxWidth(), style = AppButtonStyle.Secondary)
        Spacer(Modifier.height(12.dp))
        AppButton(text = state.listButton, onClick = onList, modifier = Modifier.fillMaxWidth(), style = AppButtonStyle.Text)
    }
}
