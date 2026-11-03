package com.galyaminsky.zachetenot.ui.screens.settings

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.galyaminsky.zachetenot.R
import com.galyaminsky.zachetenot.ui.components.AppNavigationBar
import com.galyaminsky.zachetenot.ui.components.NavItem
import com.galyaminsky.zachetenot.ui.components.VariantHeader
import com.galyaminsky.zachetenot.ui.theme.AttentionContainer
import com.galyaminsky.zachetenot.ui.theme.Background
import com.galyaminsky.zachetenot.ui.theme.Dimens
import com.galyaminsky.zachetenot.ui.theme.InfoContainer
import com.galyaminsky.zachetenot.ui.theme.Primary
import com.galyaminsky.zachetenot.ui.theme.Selected
import com.galyaminsky.zachetenot.ui.theme.Surface
import com.galyaminsky.zachetenot.ui.theme.TextPrimary
import com.galyaminsky.zachetenot.ui.theme.TextSecondary
import com.galyaminsky.zachetenot.ui.theme.DangerAction
import com.galyaminsky.zachetenot.ui.theme.DangerContainer

data class SettingsUiState(val gradeLabel: String, val soundEnabled: Boolean)

@Composable
fun SettingsScreen(state: SettingsUiState, onSoundChanged: (Boolean) -> Unit, onGradeClick: () -> Unit, onResetClick: () -> Unit, onHome: () -> Unit, onSubjects: () -> Unit, onProgress: () -> Unit, modifier: Modifier = Modifier, soundAvailable: Boolean = true) {
    Column(modifier = modifier.fillMaxSize().background(color = Background)) {
        Column(modifier = Modifier.weight(1f).verticalScroll(rememberScrollState()).padding(horizontal = Dimens.ScreenHorizontalPadding), verticalArrangement = Arrangement.spacedBy(Dimens.Space16)) {
            Spacer(modifier = Modifier.height(Dimens.Space8))
            VariantHeader(label = stringResource(R.string.screen_settings_label))
            Text(text = stringResource(R.string.nav_settings), style = MaterialTheme.typography.headlineLarge, color = TextPrimary)
            Text(text = stringResource(R.string.figma_settings_subtitle), style = MaterialTheme.typography.bodyLarge, color = TextSecondary)
            Row(modifier = Modifier.fillMaxWidth().background(color = AttentionContainer, MaterialTheme.shapes.large).padding(Dimens.Space16), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(Dimens.Space12)) {
                    Text(text = stringResource(R.string.variant_settings_label), style = MaterialTheme.typography.labelSmall, color = Primary)
                    Text(text = stringResource(R.string.figma_settings_world), style = MaterialTheme.typography.headlineMedium, color = TextPrimary)
                    Text(text = stringResource(R.string.figma_settings_world_subtitle), style = MaterialTheme.typography.bodyLarge, color = TextSecondary)
                }
                Image(painter = painterResource(R.drawable.figma_pencil), contentDescription = null)
            }
            SettingTile(title = stringResource(R.string.figma_settings_sounds), note = stringResource(R.string.figma_settings_sounds_note), icon = R.drawable.ic_music, onClick = { if (soundAvailable) onSoundChanged(!state.soundEnabled) })
            SettingTile(title = stringResource(R.string.figma_settings_grade), note = stringResource(R.string.figma_settings_grade_note, state.gradeLabel), badge = state.gradeLabel.firstOrNull()?.toString(), onClick = onGradeClick)
            SettingTile(title = stringResource(R.string.figma_settings_reset), note = stringResource(R.string.figma_settings_reset_note), icon = R.drawable.ic_reset, onClick = onResetClick, danger = true)
            Column(modifier = Modifier.fillMaxWidth().background(color = InfoContainer, MaterialTheme.shapes.large).padding(Dimens.Space16), verticalArrangement = Arrangement.spacedBy(Dimens.Space8)) {
                Text(text = stringResource(R.string.figma_settings_offline), style = MaterialTheme.typography.titleLarge, color = TextPrimary)
                Text(text = stringResource(R.string.figma_settings_offline_note), style = MaterialTheme.typography.bodyLarge, color = TextSecondary)
            }
            Spacer(modifier = Modifier.height(Dimens.Space8))
        }
        AppNavigationBar(items = listOf(NavItem(stringResource(R.string.home_nav_home), R.drawable.ic_nav_home), NavItem(stringResource(R.string.home_nav_subjects), R.drawable.ic_nav_grid), NavItem(stringResource(R.string.nav_progress), R.drawable.ic_nav_progress)), selectedIndex = -1, onSelect = { index -> when (index) { 0 -> onHome(); 1 -> onSubjects(); 2 -> onProgress() } })
    }
}

@Composable
private fun SettingTile(title: String, note: String, onClick: () -> Unit, @DrawableRes icon: Int? = null, badge: String? = null, danger: Boolean = false) {
    Row(modifier = Modifier.fillMaxWidth().background(color = if (danger) DangerContainer else Surface, MaterialTheme.shapes.large).clickable(onClick = onClick).padding(Dimens.Space16), horizontalArrangement = Arrangement.spacedBy(Dimens.Space12), verticalAlignment = Alignment.CenterVertically) {
        Box(modifier = Modifier.size(Dimens.Space48).background(color = if (danger) DangerAction else Selected, MaterialTheme.shapes.medium), contentAlignment = Alignment.Center) {
            if (icon != null) Icon(painter = painterResource(icon), contentDescription = null, modifier = Modifier.size(Dimens.Space24), tint = if (danger) Surface else Primary)
            else if (badge != null) Text(text = badge, style = MaterialTheme.typography.titleLarge, color = Primary)
        }
        Column(verticalArrangement = Arrangement.spacedBy(Dimens.Space4)) {
            Text(text = title, style = MaterialTheme.typography.titleLarge, color = if (danger) DangerAction else TextPrimary)
            Text(text = note, style = MaterialTheme.typography.bodyLarge, color = if (danger) DangerAction else TextSecondary)
        }
    }
}


