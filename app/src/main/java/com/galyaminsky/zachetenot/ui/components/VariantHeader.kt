package com.galyaminsky.zachetenot.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.galyaminsky.zachetenot.ui.theme.TextSecondary
import com.galyaminsky.zachetenot.ui.theme.Selected
import com.galyaminsky.zachetenot.ui.theme.Primary
import com.galyaminsky.zachetenot.ui.theme.Dimens
import androidx.compose.ui.text.style.TextAlign

@Composable
fun VariantHeader(label: String, modifier: Modifier = Modifier, onLabelClick: (() -> Unit)? = null, actionLabel: String? = null) {
    Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
        AppLogo()
        if (actionLabel != null && onLabelClick != null) {
            Text(text = actionLabel, style = MaterialTheme.typography.labelLarge, color = Primary, textAlign = TextAlign.Center, modifier = Modifier.width(Dimens.HeaderActionWidth).height(Dimens.HeaderActionHeight).background(color = Selected, shape = MaterialTheme.shapes.medium).clickable(onClick = onLabelClick).padding(horizontal = Dimens.Space12, vertical = Dimens.Space12))
        } else Text(text = label, style = MaterialTheme.typography.labelSmall, color = TextSecondary, modifier = if (onLabelClick == null) Modifier else Modifier.clickable(onClick = onLabelClick))
    }
}
