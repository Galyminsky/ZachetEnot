package com.galyaminsky.zachetenot.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.galyaminsky.zachetenot.R
import com.galyaminsky.zachetenot.ui.theme.Primary
import com.galyaminsky.zachetenot.ui.theme.TextPrimary
import com.galyaminsky.zachetenot.ui.theme.TextSecondary

@Composable
fun TestTopBar(timeText: String, onExit: () -> Unit, modifier: Modifier = Modifier) {
    Row(modifier.fillMaxWidth().height(48.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
        Box(Modifier.size(width = 88.dp, height = 48.dp).clickable(role = Role.Button, onClick = onExit), contentAlignment = Alignment.Center) {
            Text(text = stringResource(R.string.test_exit), style = MaterialTheme.typography.labelLarge, color = Primary)
        }
        Column(horizontalAlignment = Alignment.End, modifier = Modifier.padding(top = 2.dp)) {
            Text(text = stringResource(R.string.test_time_left), style = MaterialTheme.typography.bodySmall.copy(lineHeight = 18.sp), color = TextSecondary)
            Text(text = timeText, style = MaterialTheme.typography.titleLarge, color = TextPrimary)
        }
    }
}
