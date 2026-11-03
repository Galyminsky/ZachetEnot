package com.galyaminsky.zachetenot.ui.screens.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.galyaminsky.zachetenot.R
import com.galyaminsky.zachetenot.ui.components.AppNavigationBar
import com.galyaminsky.zachetenot.ui.components.NavItem
import com.galyaminsky.zachetenot.ui.theme.AttentionContainer
import com.galyaminsky.zachetenot.ui.theme.Background
import com.galyaminsky.zachetenot.ui.theme.Dimens
import com.galyaminsky.zachetenot.ui.theme.Selected
import com.galyaminsky.zachetenot.ui.theme.Surface
import com.galyaminsky.zachetenot.ui.theme.TextPrimary
import com.galyaminsky.zachetenot.ui.theme.TextSecondary

data class SettingsUiState(val gradeLabel: String, val soundEnabled: Boolean)

@Composable
fun SettingsScreen(state: SettingsUiState, onSoundChanged: (Boolean) -> Unit, onGradeClick: () -> Unit, onResetClick: () -> Unit, onHome: () -> Unit, onSubjects: () -> Unit, onProgress: () -> Unit, modifier: Modifier = Modifier, soundAvailable: Boolean = true) {
    Column(modifier = modifier.fillMaxSize().background(Background)) {
        Column(modifier = Modifier.weight(1f).verticalScroll(rememberScrollState()).padding(horizontal = Dimens.ScreenHorizontalPadding)) {
            Spacer(modifier = Modifier.height(Dimens.Space16))
            Text(text = stringResource(R.string.nav_settings), style = MaterialTheme.typography.headlineMedium, color = TextPrimary)
            Text(text = stringResource(R.string.figma_settings_subtitle), style = MaterialTheme.typography.bodyLarge, color = TextSecondary)
            Spacer(modifier = Modifier.height(Dimens.Space16))
            Row(modifier = Modifier.fillMaxWidth().background(AttentionContainer, MaterialTheme.shapes.large).padding(Dimens.Space16), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Column(verticalArrangement = Arrangement.spacedBy(Dimens.Space8)) {
                    Text(text = stringResource(R.string.figma_settings_world), style = MaterialTheme.typography.titleLarge, color = TextPrimary)
                    Text(text = stringResource(R.string.figma_settings_world_subtitle), style = MaterialTheme.typography.bodySmall, color = TextSecondary)
                }
                Image(painter = painterResource(R.drawable.figma_pencil), contentDescription = null)
            }
            Spacer(modifier = Modifier.height(Dimens.Space16))
            Row(modifier = Modifier.fillMaxWidth().background(Surface, MaterialTheme.shapes.medium).padding(Dimens.Space16), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Column {
                    Text(text = stringResource(R.string.figma_settings_sounds), style = MaterialTheme.typography.labelLarge, color = TextPrimary)
                    Text(text = stringResource(R.string.figma_settings_sounds_note), style = MaterialTheme.typography.bodySmall, color = TextSecondary)
                }
                Switch(checked = state.soundEnabled, onCheckedChange = onSoundChanged, enabled = soundAvailable)
            }
            Spacer(modifier = Modifier.height(Dimens.Space12))
            Column(modifier = Modifier.fillMaxWidth().background(Surface, MaterialTheme.shapes.medium).clickable(onClick = onGradeClick).padding(Dimens.Space16)) {
                Text(text = stringResource(R.string.figma_settings_grade), style = MaterialTheme.typography.labelLarge, color = TextPrimary)
                Text(text = stringResource(R.string.figma_settings_grade_note, state.gradeLabel), style = MaterialTheme.typography.bodySmall, color = TextSecondary)
            }
            Spacer(modifier = Modifier.height(Dimens.Space12))
            Column(modifier = Modifier.fillMaxWidth().background(Surface, MaterialTheme.shapes.medium).clickable(onClick = onResetClick).padding(Dimens.Space16)) {
                Text(text = stringResource(R.string.figma_settings_reset), style = MaterialTheme.typography.labelLarge, color = TextPrimary)
                Text(text = stringResource(R.string.figma_settings_reset_note), style = MaterialTheme.typography.bodySmall, color = TextSecondary)
            }
            Spacer(modifier = Modifier.height(Dimens.Space16))
            Column(modifier = Modifier.fillMaxWidth().background(Selected, MaterialTheme.shapes.large).padding(Dimens.Space16), verticalArrangement = Arrangement.spacedBy(Dimens.Space8)) {
                Text(text = stringResource(R.string.figma_settings_offline), style = MaterialTheme.typography.titleLarge, color = TextPrimary)
                Text(text = stringResource(R.string.figma_settings_offline_note), style = MaterialTheme.typography.bodySmall, color = TextSecondary)
            }
            Spacer(modifier = Modifier.height(Dimens.Space16))
        }
        AppNavigationBar(items = listOf(NavItem(stringResource(R.string.home_nav_home), R.drawable.ic_logo), NavItem(stringResource(R.string.home_nav_subjects), R.drawable.ic_layers), NavItem(stringResource(R.string.nav_progress), R.drawable.ic_chart)), selectedIndex = -1, onSelect = { index -> when (index) { 0 -> onHome(); 1 -> onSubjects(); 2 -> onProgress() } })
    }
}
