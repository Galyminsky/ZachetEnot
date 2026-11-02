package com.galyaminsky.zachetenot.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.galyaminsky.zachetenot.R
import com.galyaminsky.zachetenot.ui.theme.Primary
import com.galyaminsky.zachetenot.ui.theme.TextPrimary
import com.galyaminsky.zachetenot.ui.theme.TextSecondary

@Composable
fun TestProgressHeader(contextLine: String, title: String, questionNumber: Int, total: Int, answered: Int, modifier: Modifier = Modifier) {
    Column(modifier.fillMaxWidth()) {
        Text(text = contextLine, style = MaterialTheme.typography.labelMedium, color = TextSecondary)
        Spacer(Modifier.height(12.dp))
        Text(text = title, style = MaterialTheme.typography.titleLarge, color = TextPrimary)
        Spacer(Modifier.height(12.dp))
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Text(text = stringResource(R.string.question_n_of, questionNumber, total), style = MaterialTheme.typography.bodySmall.copy(lineHeight = 18.sp), color = TextPrimary)
            Text(text = stringResource(R.string.answered_n_of, answered, total), style = MaterialTheme.typography.labelMedium, color = TextSecondary)
        }
        Spacer(Modifier.height(12.dp))
        StepProgressBar(progress = answered.toFloat() / total, fillColor = Primary)
    }
}
