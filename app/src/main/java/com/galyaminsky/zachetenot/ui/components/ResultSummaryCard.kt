package com.galyaminsky.zachetenot.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight
import com.galyaminsky.zachetenot.R
import com.galyaminsky.zachetenot.domain.progress.TopicStatus
import com.galyaminsky.zachetenot.ui.theme.Border
import com.galyaminsky.zachetenot.ui.theme.Dimens
import com.galyaminsky.zachetenot.ui.theme.Primary
import com.galyaminsky.zachetenot.ui.theme.Selected
import com.galyaminsky.zachetenot.ui.theme.Success
import com.galyaminsky.zachetenot.ui.theme.Surface
import com.galyaminsky.zachetenot.ui.theme.TextPrimary
import com.galyaminsky.zachetenot.ui.theme.TextSecondary

@Composable
fun ResultSummaryCard(
    passed: Boolean,
    verdictTitle: String,
    verdictNote: String?,
    correctText: String,
    percentText: String,
    correctLabel: String,
    completion: Float,
    completionText: String,
    modifier: Modifier = Modifier
) {
    val shape = MaterialTheme.shapes.large
    Column(
        modifier.fillMaxWidth().clip(shape).background(Surface)
            .border(Dimens.BorderWidth, Border, shape).padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(Modifier.size(52.dp).clip(MaterialTheme.shapes.medium).background(Selected), contentAlignment = Alignment.Center) {
                Icon(painter = painterResource(if (passed) R.drawable.ic_check else R.drawable.ic_book_open), contentDescription = null, modifier = Modifier.size(24.dp), tint = Primary)
            }
            Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(text = verdictTitle, style = MaterialTheme.typography.titleLarge, color = TextPrimary)
                if (passed) {
                    StatusChip(status = TopicStatus.PASSED)
                } else if (verdictNote != null) {
                    Text(text = verdictNote, style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold), color = Primary)
                }
            }
        }
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.Bottom) {
            Text(text = correctText, style = MaterialTheme.typography.headlineLarge, color = TextPrimary)
            Text(text = percentText, style = MaterialTheme.typography.titleLarge, color = if (passed) Success else Primary)
        }
        Text(text = correctLabel, style = MaterialTheme.typography.labelMedium, color = TextSecondary)
        StepProgressBar(progress = completion, modifier = Modifier.fillMaxWidth())
        Text(text = completionText, style = MaterialTheme.typography.labelMedium, color = TextSecondary)
    }
}
