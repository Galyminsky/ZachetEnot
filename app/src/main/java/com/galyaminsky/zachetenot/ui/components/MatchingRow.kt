package com.galyaminsky.zachetenot.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.galyaminsky.zachetenot.R
import com.galyaminsky.zachetenot.ui.theme.Border
import com.galyaminsky.zachetenot.ui.theme.Dimens
import com.galyaminsky.zachetenot.ui.theme.Primary
import com.galyaminsky.zachetenot.ui.theme.Selected
import com.galyaminsky.zachetenot.ui.theme.Surface
import com.galyaminsky.zachetenot.ui.theme.TextPrimary
import com.galyaminsky.zachetenot.ui.theme.TextSecondary

@Composable
fun MatchingRow(prompt: String, chosen: String?, locked: Boolean, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.fillMaxWidth().height(56.dp).clip(MaterialTheme.shapes.medium)
            .background(if (chosen != null) Selected else Surface)
            .border(if (chosen != null) Dimens.BorderWidthSelected else Dimens.BorderWidth, if (chosen != null) Primary else Border, MaterialTheme.shapes.medium)
            .clickable(enabled = !locked, role = Role.Button, onClick = onClick)
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = prompt, style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium), color = TextPrimary, modifier = Modifier.widthIn(min = 50.dp))
        Icon(painter = painterResource(R.drawable.ic_arrow_right), contentDescription = null, modifier = Modifier.size(18.dp), tint = Primary)
        Text(text = chosen ?: stringResource(R.string.matching_choose), style = MaterialTheme.typography.bodyLarge, color = if (chosen == null) TextSecondary else TextPrimary, modifier = Modifier.weight(1f))
        if (locked) Icon(painter = painterResource(R.drawable.ic_lock), contentDescription = null, modifier = Modifier.size(18.dp), tint = Primary)
    }
}
