package com.galyaminsky.zachetenot.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.foundation.selection.selectable
import androidx.compose.ui.unit.dp
import com.galyaminsky.zachetenot.R
import com.galyaminsky.zachetenot.ui.theme.Border
import com.galyaminsky.zachetenot.ui.theme.CapsuleShape
import com.galyaminsky.zachetenot.ui.theme.Dimens
import com.galyaminsky.zachetenot.ui.theme.Primary
import com.galyaminsky.zachetenot.ui.theme.Selected
import com.galyaminsky.zachetenot.ui.theme.Surface
import com.galyaminsky.zachetenot.ui.theme.TextPrimary

@Composable
fun ChoiceOption(text: String, selected: Boolean, locked: Boolean, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Row(modifier.fillMaxWidth().height(56.dp).clip(MaterialTheme.shapes.medium).background(if (selected) Selected else Surface).border(if (selected) Dimens.BorderWidthSelected else Dimens.BorderWidth, if (selected) Primary else Border, MaterialTheme.shapes.medium).selectable(selected = selected, enabled = !locked, role = Role.RadioButton, onClick = onClick).padding(horizontal = Dimens.Space16), horizontalArrangement = Arrangement.spacedBy(Dimens.Space12), verticalAlignment = Alignment.CenterVertically) {
        RadioMark(selected)
        Text(text = text, style = MaterialTheme.typography.bodyLarge, color = TextPrimary, modifier = Modifier.weight(1f))
        if (locked && selected) Icon(painter = painterResource(R.drawable.ic_lock), contentDescription = null, modifier = Modifier.size(Dimens.Space18), tint = Primary)
    }
}

@Composable
private fun RadioMark(selected: Boolean) {
    Box(Modifier.size(Dimens.Space24).clip(CapsuleShape).border(Dimens.BorderWidthSelected, if (selected) Primary else Border, CapsuleShape), contentAlignment = Alignment.Center) {
        if (selected) Box(Modifier.size(Dimens.Space12).clip(CapsuleShape).background(Primary))
    }
}
