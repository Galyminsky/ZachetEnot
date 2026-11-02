package com.galyaminsky.zachetenot.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.galyaminsky.zachetenot.ui.theme.Border
import com.galyaminsky.zachetenot.ui.theme.Disabled
import com.galyaminsky.zachetenot.ui.theme.Dimens
import com.galyaminsky.zachetenot.ui.theme.Primary
import com.galyaminsky.zachetenot.ui.theme.Selected
import com.galyaminsky.zachetenot.ui.theme.Surface
import com.galyaminsky.zachetenot.ui.theme.TextDisabled
import com.galyaminsky.zachetenot.ui.theme.TextPrimary
import com.galyaminsky.zachetenot.ui.theme.TextSecondary

@Composable
fun SubjectCard(
    title: String,
    subtitle: String,
    state: SelectionState,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    iconText: String? = null,
    @DrawableRes icon: Int? = null
) {
    val shape = MaterialTheme.shapes.large
    val backgroundColor = when (state) {
        SelectionState.Default -> Surface
        SelectionState.Selected -> Selected
        SelectionState.Locked -> Surface
    }
    val borderColor = if (state == SelectionState.Selected) Primary else Border
    val borderWidth = if (state == SelectionState.Selected) Dimens.BorderWidthSelected else Dimens.BorderWidth

    Row(
        modifier = modifier.fillMaxWidth().height(92.dp).clip(shape).background(backgroundColor)
            .border(borderWidth, borderColor, shape)
            .selectable(
                selected = state == SelectionState.Selected,
                enabled = state != SelectionState.Locked,
                role = Role.RadioButton,
                onClick = onClick
            )
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.size(52.dp).clip(MaterialTheme.shapes.medium)
                .background(if (state == SelectionState.Locked) Disabled else Primary),
            contentAlignment = Alignment.Center
        ) {
            when {
                iconText != null -> Text(
                    text = iconText,
                    style = MaterialTheme.typography.titleLarge.copy(fontSize = 23.sp, lineHeight = 28.sp),
                    color = Surface
                )
                icon != null -> Icon(
                    painter = painterResource(icon),
                    contentDescription = null,
                    modifier = Modifier.size(26.dp),
                    tint = if (state == SelectionState.Locked) TextDisabled else Surface
                )
            }
        }
        Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(5.dp)) {
            Text(
                text = title,
                style = MaterialTheme.typography.labelLarge,
                color = if (state == SelectionState.Locked) TextSecondary else TextPrimary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = when (state) {
                    SelectionState.Selected -> Primary
                    SelectionState.Default -> TextSecondary
                    SelectionState.Locked -> TextDisabled
                },
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        SelectionIndicator(state)
    }
}
