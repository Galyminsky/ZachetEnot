package com.galyaminsky.zachetenot.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.galyaminsky.zachetenot.R

@Composable
fun AppLogo(modifier: Modifier = Modifier) {
    Image(painter = painterResource(R.drawable.zachetenot_logo_horizontal), contentDescription = null, modifier = modifier.size(width = 123.dp, height = 44.dp))
}
