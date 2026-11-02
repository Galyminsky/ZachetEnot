package com.galyaminsky.zachetenot.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.galyaminsky.zachetenot.R
import com.galyaminsky.zachetenot.domain.progress.TopicStatus
import com.galyaminsky.zachetenot.ui.theme.Disabled
import com.galyaminsky.zachetenot.ui.theme.Primary
import com.galyaminsky.zachetenot.ui.theme.SelectedStrong
import com.galyaminsky.zachetenot.ui.theme.Success
import com.galyaminsky.zachetenot.ui.theme.SuccessContainer
import com.galyaminsky.zachetenot.ui.theme.TextSecondary

@Composable
fun StatusChip(status: TopicStatus, modifier: Modifier = Modifier) {
    val backgroundColor = when (status) {
        TopicStatus.AVAILABLE -> SelectedStrong
        TopicStatus.PASSED -> SuccessContainer
        TopicStatus.LOCKED -> Disabled
    }
    val contentColor = when (status) {
        TopicStatus.AVAILABLE -> Primary
        TopicStatus.PASSED -> Success
        TopicStatus.LOCKED -> TextSecondary
    }
    val iconRes = when (status) {
        TopicStatus.AVAILABLE -> R.drawable.ic_play_small
        TopicStatus.PASSED -> R.drawable.ic_check_small
        TopicStatus.LOCKED -> R.drawable.ic_lock_small
    }
    val labelRes = when (status) {
        TopicStatus.AVAILABLE -> R.string.topic_status_available
        TopicStatus.PASSED -> R.string.topic_status_passed
        TopicStatus.LOCKED -> R.string.topic_status_locked
    }

    Row(
        modifier = modifier
            .background(backgroundColor, MaterialTheme.shapes.extraSmall)
            .padding(horizontal = 8.dp, vertical = 5.dp),
        horizontalArrangement = Arrangement.spacedBy(5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(iconRes),
            contentDescription = null,
            tint = contentColor,
            modifier = Modifier.size(12.dp)
        )
        Text(
            text = stringResource(labelRes),
            color = contentColor,
            style = MaterialTheme.typography.labelSmall
        )
    }
}
