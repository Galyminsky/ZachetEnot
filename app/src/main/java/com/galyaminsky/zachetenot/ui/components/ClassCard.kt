package com.galyaminsky.zachetenot.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.Image
import com.galyaminsky.zachetenot.R
import com.galyaminsky.zachetenot.ui.theme.Border
import com.galyaminsky.zachetenot.ui.theme.Dimens
import com.galyaminsky.zachetenot.ui.theme.Primary
import com.galyaminsky.zachetenot.ui.theme.Selected
import com.galyaminsky.zachetenot.ui.theme.Surface
import com.galyaminsky.zachetenot.ui.theme.TextDisabled
import com.galyaminsky.zachetenot.ui.theme.TextPrimary
import com.galyaminsky.zachetenot.ui.theme.GradeOne
import com.galyaminsky.zachetenot.ui.theme.GradeTwo
import com.galyaminsky.zachetenot.ui.theme.AttentionContainer
import com.galyaminsky.zachetenot.ui.theme.InfoContainer
import com.galyaminsky.zachetenot.ui.theme.PositiveInfoContainer
import com.galyaminsky.zachetenot.ui.theme.Success
import com.galyaminsky.zachetenot.ui.theme.TextSecondary
import com.galyaminsky.zachetenot.ui.theme.CapsuleShape
import com.galyaminsky.zachetenot.ui.theme.CardShape20
import com.galyaminsky.zachetenot.ui.theme.ClassNumber

@Composable
fun ClassCard(
    grade: Int,
    state: SelectionState,
    @DrawableRes illustration: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val shape = MaterialTheme.shapes.large
    val gradeColor = when (grade) { 1 -> GradeOne; 2 -> GradeTwo; 4 -> com.galyaminsky.zachetenot.ui.theme.Success; else -> Primary }
    val backgroundColor = if (state == SelectionState.Selected) Selected else Surface
    val borderColor = if (state == SelectionState.Selected) Primary else Border
    val borderWidth = if (state == SelectionState.Selected) Dimens.BorderWidthSelected else Dimens.BorderWidth
    val textColor = if (state == SelectionState.Locked) TextDisabled else TextPrimary

    Box(
        modifier = modifier
            .height(Dimens.ClassCardHeight)
            .clip(shape)
            .background(color = backgroundColor)
            .border(width = borderWidth, color = borderColor, shape = shape)
            .selectable(
                selected = state == SelectionState.Selected,
                enabled = state != SelectionState.Locked,
                role = Role.RadioButton,
                onClick = onClick
            )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(Dimens.ClassCardPadding),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Column {
                Text(text = grade.toString(), style = ClassNumber, color = if (state == SelectionState.Locked) TextDisabled else Primary)
                Text(text = stringResource(R.string.grade_label), style = MaterialTheme.typography.titleSmall, color = textColor)
            }
            if (state != SelectionState.Default) Box(modifier = Modifier.size(Dimens.ClassStatusSize).background(color = if (state == SelectionState.Selected) Primary else Surface, shape = CapsuleShape).border(width = Dimens.BorderWidthSelected, color = if (state == SelectionState.Selected) Primary else Border, shape = CapsuleShape), contentAlignment = Alignment.Center) {
                Icon(painter = painterResource(if (state == SelectionState.Selected) R.drawable.ic_check_small else R.drawable.ic_lock_small), contentDescription = null, modifier = Modifier.size(Dimens.ClassStatusIconSize), tint = if (state == SelectionState.Selected) Surface else TextDisabled)
            }
        }
        Box(modifier = Modifier.align(Alignment.BottomEnd).padding(end = Dimens.ClassIllustrationEnd, bottom = Dimens.ClassIllustrationBottom).size(width = Dimens.ClassIllustrationSlotWidth, height = Dimens.ClassIllustrationSlotHeight).clipToBounds()) {
            Image(painter = painterResource(illustration), contentDescription = null, modifier = Modifier.requiredSize(width = Dimens.ClassIllustrationWidth, height = Dimens.ClassIllustrationHeight))
        }
    }
}

