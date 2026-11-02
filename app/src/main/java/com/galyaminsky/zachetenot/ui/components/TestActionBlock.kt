package com.galyaminsky.zachetenot.ui.components

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.galyaminsky.zachetenot.R
import com.galyaminsky.zachetenot.ui.theme.Primary
import com.galyaminsky.zachetenot.ui.theme.TextSecondary

@Composable
fun TestActionBlock(buttonText: String, buttonEnabled: Boolean, onButtonClick: () -> Unit, caption: String, modifier: Modifier = Modifier, showLockedNotice: Boolean = false) {
    Column(modifier.fillMaxWidth().padding(top = 24.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        if (showLockedNotice) LockedNotice()
        AppButton(buttonText, onButtonClick, Modifier.fillMaxWidth(), enabled = buttonEnabled, trailingIcon = if (buttonEnabled) R.drawable.ic_arrow_right else null)
        Text(text = caption, style = MaterialTheme.typography.bodySmall.copy(lineHeight = 18.sp), color = TextSecondary, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
    }
}

@Composable
private fun LockedNotice() {
    Row(horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.Top) {
        Icon(painter = painterResource(R.drawable.ic_lock), contentDescription = null, modifier = Modifier.size(20.dp), tint = Primary)
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(text = stringResource(R.string.answer_locked_title), style = MaterialTheme.typography.bodyLarge, color = Primary)
            Text(text = stringResource(R.string.answer_locked_text), style = MaterialTheme.typography.bodySmall.copy(lineHeight = 18.sp), color = TextSecondary)
        }
    }
}
