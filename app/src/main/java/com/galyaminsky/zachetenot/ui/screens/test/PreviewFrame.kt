package com.galyaminsky.zachetenot.ui.screens.test

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.galyaminsky.zachetenot.ui.theme.Background
import com.galyaminsky.zachetenot.ui.theme.CapsuleShape
import com.galyaminsky.zachetenot.ui.theme.TextPrimary
import com.galyaminsky.zachetenot.ui.theme.ZachetEnotTheme

@Composable
internal fun PreviewFrame(content: @Composable () -> Unit) {
    ZachetEnotTheme {
        Column(Modifier.fillMaxSize().background(Background)) {
            Spacer(Modifier.height(48.dp))
            Box(Modifier.weight(1f)) { content() }
            Box(Modifier.fillMaxWidth().height(28.dp), contentAlignment = Alignment.Center) {
                Box(Modifier.width(120.dp).height(4.dp).clip(CapsuleShape).background(TextPrimary))
            }
        }
    }
}
