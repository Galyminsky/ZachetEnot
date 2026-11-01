package com.galyaminsky.zachetenot.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.galyaminsky.zachetenot.ui.theme.TextSecondary

@Composable
fun HintRow(
    @DrawableRes icon: Int,
    text: String,
    modifier: Modifier = Modifier,
    iconSize: Dp = 18.dp,
    iconTint: Color = TextSecondary,
    textStyle: TextStyle = MaterialTheme.typography.bodySmall.copy(fontSize = 13.sp, lineHeight = 20.sp),
    spacing: Dp = 10.dp
) {
    Row(modifier = modifier.padding(horizontal = 4.dp), horizontalArrangement = Arrangement.spacedBy(spacing), verticalAlignment = Alignment.Top) {
        Icon(painter = painterResource(icon), contentDescription = null, modifier = Modifier.size(iconSize), tint = iconTint)
        Text(text = text, style = textStyle, color = TextSecondary)
    }
}
