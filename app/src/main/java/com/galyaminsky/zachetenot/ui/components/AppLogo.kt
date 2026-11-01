package com.galyaminsky.zachetenot.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
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
import com.galyaminsky.zachetenot.ui.theme.TextPrimary

@Composable
fun AppLogo(modifier: Modifier = Modifier) {
    Row(modifier = modifier, horizontalArrangement = Arrangement.spacedBy(9.dp), verticalAlignment = Alignment.CenterVertically) {
        Image(painter = painterResource(R.drawable.ic_logo), contentDescription = null, modifier = Modifier.size(34.dp))
        Text(text = stringResource(R.string.app_wordmark), style = MaterialTheme.typography.headlineMedium.copy(fontSize = 27.sp, lineHeight = 33.sp, letterSpacing = (-1).sp), color = TextPrimary)
    }
}
