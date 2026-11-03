package com.galyaminsky.zachetenot.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.galyaminsky.zachetenot.R
import com.galyaminsky.zachetenot.domain.progress.TopicStatus
import com.galyaminsky.zachetenot.ui.theme.Border
import com.galyaminsky.zachetenot.ui.theme.Disabled
import com.galyaminsky.zachetenot.ui.theme.Dimens
import com.galyaminsky.zachetenot.ui.theme.Primary
import com.galyaminsky.zachetenot.ui.theme.Selected
import com.galyaminsky.zachetenot.ui.theme.Success
import com.galyaminsky.zachetenot.ui.theme.SuccessContainer
import com.galyaminsky.zachetenot.ui.theme.Surface
import com.galyaminsky.zachetenot.ui.theme.TextDisabled
import com.galyaminsky.zachetenot.ui.theme.TextPrimary
import com.galyaminsky.zachetenot.ui.theme.TextSecondary

@Composable
fun TopicCard(
    number: Int,
    title: String,
    description: String,
    status: TopicStatus,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val backgroundColor = if (status == TopicStatus.AVAILABLE) Selected else Surface
    val borderColor = if (status == TopicStatus.AVAILABLE) Primary else Border
    val borderWidth = if (status == TopicStatus.AVAILABLE) Dimens.BorderWidthSelected else Dimens.BorderWidth
    val badgeColor = when (status) {
        TopicStatus.AVAILABLE -> Primary
        TopicStatus.PASSED -> SuccessContainer
        TopicStatus.LOCKED -> Disabled
    }
    val numberColor = when (status) {
        TopicStatus.PASSED -> Success
        TopicStatus.LOCKED -> TextDisabled
        TopicStatus.AVAILABLE -> Surface
    }
    val titleColor = if (status == TopicStatus.LOCKED) TextSecondary else TextPrimary

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.large)
            .background(backgroundColor)
            .border(borderWidth, borderColor, MaterialTheme.shapes.large)
            .clickable(enabled = status != TopicStatus.LOCKED, role = Role.Button, onClick = onClick)
            .padding(Dimens.Space16),
        horizontalArrangement = Arrangement.spacedBy(Dimens.Space12),
        verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier.size(36.dp).clip(MaterialTheme.shapes.small).background(badgeColor),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "%02d".format(number),
                style = MaterialTheme.typography.labelLarge.copy(fontSize = 15.sp, lineHeight = 18.sp),
                color = numberColor
            )
        }
        Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(7.dp)) {
            Text(
                text = title,
                style = MaterialTheme.typography.labelLarge.copy(lineHeight = 20.sp),
                color = titleColor,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall.copy(fontSize = 13.sp, lineHeight = 18.sp),
                color = TextSecondary
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                StatusChip(status)
                if (status != TopicStatus.LOCKED) {
                    Icon(
                        painter = painterResource(R.drawable.ic_arrow_right),
                        contentDescription = null,
                modifier = Modifier.size(Dimens.Space20),
                        tint = if (status == TopicStatus.AVAILABLE) Primary else TextSecondary
                    )
                }
            }
        }
    }
}
