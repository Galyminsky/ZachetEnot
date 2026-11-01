package com.galyaminsky.zachetenot.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import com.galyaminsky.zachetenot.R
import com.galyaminsky.zachetenot.ui.theme.Border
import com.galyaminsky.zachetenot.ui.theme.Dimens
import com.galyaminsky.zachetenot.ui.theme.Primary
import com.galyaminsky.zachetenot.ui.theme.Selected
import com.galyaminsky.zachetenot.ui.theme.Surface
import com.galyaminsky.zachetenot.ui.theme.TextDisabled
import com.galyaminsky.zachetenot.ui.theme.TextPrimary

@Composable
fun ClassCard(
    grade: Int,
    state: SelectionState,
    @DrawableRes illustration: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val shape = MaterialTheme.shapes.large
    val backgroundColor = if (state == SelectionState.Selected) Selected else Surface
    val borderColor = if (state == SelectionState.Selected) Primary else Border
    val borderWidth = if (state == SelectionState.Selected) Dimens.BorderWidthSelected else Dimens.BorderWidth
    val textColor = if (state == SelectionState.Locked) TextDisabled else TextPrimary

    Box(
        modifier = modifier
            .height(174.dp)
            .clip(shape)
            .background(backgroundColor)
            .border(borderWidth, borderColor, shape)
            .selectable(
                selected = state == SelectionState.Selected,
                enabled = state != SelectionState.Locked,
                role = Role.RadioButton,
                onClick = onClick
            )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(18.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Column {
                Text(grade.toString(), style = MaterialTheme.typography.displayLarge, color = if (state == SelectionState.Locked) TextDisabled else Primary)
                Text(stringResource(R.string.grade_label), style = MaterialTheme.typography.titleSmall, color = textColor)
            }
            SelectionIndicator(state)
        }
        Image(
            painter = painterResource(illustration),
            contentDescription = null,
            modifier = Modifier.align(Alignment.BottomEnd).padding(end = 8.dp, bottom = 6.dp).size(width = 104.dp, height = 88.dp)
        )
    }
}
