package com.galyaminsky.zachetenot.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.galyaminsky.zachetenot.R
import com.galyaminsky.zachetenot.ui.theme.Accent
import com.galyaminsky.zachetenot.ui.theme.OnPrimaryMuted
import com.galyaminsky.zachetenot.ui.theme.Primary
import com.galyaminsky.zachetenot.ui.theme.Surface

@Composable
fun ProgressCard(passedCount: Int, totalCount: Int, percent: Int, message: String, modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxWidth().background(Primary, MaterialTheme.shapes.large).padding(24.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Text(text = stringResource(R.string.progress_title), style = MaterialTheme.typography.titleSmall, color = Surface)
            Icon(painter = painterResource(R.drawable.ic_sparkles), contentDescription = null, modifier = Modifier.size(21.dp), tint = Accent)
        }
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Text(text = stringResource(R.string.count_of_total, passedCount, totalCount), style = MaterialTheme.typography.headlineLarge.copy(lineHeight = 39.sp, letterSpacing = (-1).sp), color = Surface, modifier = Modifier.alignByBaseline())
            Spacer(modifier = Modifier.size(8.dp))
            Text(text = stringResource(R.string.progress_topics_done), style = MaterialTheme.typography.bodySmall, color = OnPrimaryMuted, modifier = Modifier.alignByBaseline())
            Spacer(modifier = Modifier.weight(1f))
            Text(text = stringResource(R.string.percent_format, percent), style = MaterialTheme.typography.titleLarge.copy(lineHeight = 24.sp), color = Accent, modifier = Modifier.alignByBaseline())
        }
        StepProgressBar(progress = percent / 100f, trackColor = Surface.copy(alpha = 0.25f), fillColor = Accent)
        Text(text = message, style = MaterialTheme.typography.bodySmall, color = OnPrimaryMuted)
    }
}
