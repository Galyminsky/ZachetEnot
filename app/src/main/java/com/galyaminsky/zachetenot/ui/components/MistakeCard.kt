package com.galyaminsky.zachetenot.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.galyaminsky.zachetenot.ui.theme.Background
import com.galyaminsky.zachetenot.ui.theme.Border
import com.galyaminsky.zachetenot.ui.theme.Dimens
import com.galyaminsky.zachetenot.ui.theme.Primary
import com.galyaminsky.zachetenot.ui.theme.Surface
import com.galyaminsky.zachetenot.ui.theme.TextPrimary
import com.galyaminsky.zachetenot.ui.theme.TextSecondary

data class MistakeUi(
    val questionLabel: String,
    val questionText: String,
    val yourAnswerLabel: String,
    val yourAnswer: String,
    val correctLabel: String,
    val correctAnswer: String,
    val ruleTitle: String,
    val ruleText: String
)

@Composable
fun MistakeCard(mistake: MistakeUi, modifier: Modifier = Modifier) {
    val shape = MaterialTheme.shapes.large
    Column(
        modifier.fillMaxWidth().clip(shape).background(Surface)
            .border(Dimens.BorderWidth, Border, shape).padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(text = mistake.questionLabel, style = MaterialTheme.typography.labelMedium, color = Primary)
        Text(text = mistake.questionText, style = MaterialTheme.typography.titleLarge, color = TextPrimary)
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(text = mistake.yourAnswerLabel, style = MaterialTheme.typography.labelMedium, color = TextSecondary)
            Text(text = mistake.yourAnswer, style = MaterialTheme.typography.bodyLarge, color = TextPrimary)
        }
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(text = mistake.correctLabel, style = MaterialTheme.typography.labelMedium, color = Primary)
            Text(text = mistake.correctAnswer, style = MaterialTheme.typography.bodyLarge, color = TextPrimary)
        }
        Column(Modifier.fillMaxWidth().clip(MaterialTheme.shapes.medium).background(Background).padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(text = mistake.ruleTitle, style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold), color = Primary)
            Text(text = mistake.ruleText, style = MaterialTheme.typography.bodyLarge, color = TextPrimary)
        }
    }
}
