package com.galyaminsky.zachetenot.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.galyaminsky.zachetenot.R
import com.galyaminsky.zachetenot.ui.theme.Border
import com.galyaminsky.zachetenot.ui.theme.CapsuleShape
import com.galyaminsky.zachetenot.ui.theme.Disabled
import com.galyaminsky.zachetenot.ui.theme.Primary
import com.galyaminsky.zachetenot.ui.theme.Surface
import com.galyaminsky.zachetenot.ui.theme.TextDisabled

enum class SelectionState { Default, Selected, Locked }

@Composable
fun SelectionIndicator(state: SelectionState, modifier: Modifier = Modifier) {
    val backgroundColor = when (state) {
        SelectionState.Default -> Surface
        SelectionState.Selected -> Primary
        SelectionState.Locked -> Disabled
    }

    Box(
        modifier = modifier
            .size(26.dp)
            .clip(CapsuleShape)
            .background(backgroundColor)
            .then(if (state == SelectionState.Default) Modifier.border(2.dp, Border, CapsuleShape) else Modifier),
        contentAlignment = Alignment.Center
    ) {
        when (state) {
            SelectionState.Default -> Unit
            SelectionState.Selected -> Icon(
                painter = painterResource(R.drawable.ic_check_small),
                contentDescription = null,
                tint = Surface,
                modifier = Modifier.size(15.dp)
            )
            SelectionState.Locked -> Icon(
                painter = painterResource(R.drawable.ic_lock_small),
                contentDescription = null,
                tint = TextDisabled,
                modifier = Modifier.size(15.dp)
            )
        }
    }
}
