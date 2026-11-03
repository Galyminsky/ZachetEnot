package com.galyaminsky.zachetenot.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import com.galyaminsky.zachetenot.ui.theme.Dimens
import com.galyaminsky.zachetenot.ui.theme.Primary
import com.galyaminsky.zachetenot.ui.theme.Selected
import com.galyaminsky.zachetenot.ui.theme.Surface
import com.galyaminsky.zachetenot.ui.theme.TextSecondary

data class NavItem(val label: String, @DrawableRes val icon: Int)

@Composable
fun AppNavigationBar(items: List<NavItem>, selectedIndex: Int, onSelect: (Int) -> Unit, modifier: Modifier = Modifier) {
    Row(modifier = modifier.fillMaxWidth().padding(horizontal = Dimens.ScreenHorizontalPadding, vertical = Dimens.Space8).background(color = Surface, MaterialTheme.shapes.large).padding(Dimens.Space8), horizontalArrangement = Arrangement.spacedBy(Dimens.Space8)) {
        items.forEachIndexed { index, item ->
            val selected = index == selectedIndex
            val color = if (selected) Primary else TextSecondary
            Column(modifier = Modifier.weight(1f).height(Dimens.Space48 + Dimens.Space8).background(color = if (selected) Selected else Surface, MaterialTheme.shapes.medium).selectable(selected = selected, role = Role.Tab, onClick = { onSelect(index) }).padding(Dimens.Space4), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                Icon(painter = painterResource(item.icon), contentDescription = null, modifier = Modifier.size(Dimens.Space24), tint = color)
                Text(text = item.label, style = MaterialTheme.typography.labelSmall, color = color)
            }
        }
    }
}

