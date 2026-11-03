package com.galyaminsky.zachetenot.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import com.galyaminsky.zachetenot.R
import com.galyaminsky.zachetenot.ui.theme.Primary
import com.galyaminsky.zachetenot.ui.theme.Dimens

@Composable
fun BackButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
    Row(modifier = modifier.heightIn(min = Dimens.MinTouchTarget).clickable(role = Role.Button, onClick = onClick), horizontalArrangement = Arrangement.spacedBy(Dimens.Space8), verticalAlignment = Alignment.CenterVertically) {
        Icon(painter = painterResource(R.drawable.ic_arrow_left), contentDescription = null, modifier = Modifier.size(Dimens.Space20), tint = Primary)
        Text(text = stringResource(R.string.back), style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold), color = Primary)
    }
}
