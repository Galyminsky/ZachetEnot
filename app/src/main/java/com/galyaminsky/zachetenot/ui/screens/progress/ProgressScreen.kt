package com.galyaminsky.zachetenot.ui.screens.progress

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
import com.galyaminsky.zachetenot.ui.theme.Primary
import com.galyaminsky.zachetenot.ui.theme.Selected
import com.galyaminsky.zachetenot.ui.theme.Surface
import com.galyaminsky.zachetenot.ui.theme.TextPrimary
import com.galyaminsky.zachetenot.ui.theme.TextSecondary

data class ProgressUiState(val gradeLabel: String, val subjectName: String, val passedCount: Int, val totalCount: Int, val percent: Int, val availableCount: Int, val lockedCount: Int)

@Composable
fun ProgressScreen(state: ProgressUiState, onHome: () -> Unit, onSubjects: () -> Unit, onTopics: () -> Unit, modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxSize().background(color = Background)) {
        Column(modifier = Modifier.weight(1f).verticalScroll(rememberScrollState()).padding(horizontal = Dimens.ScreenHorizontalPadding), verticalArrangement = Arrangement.spacedBy(Dimens.Space16)) {
            Spacer(modifier = Modifier.height(Dimens.Space8))
            VariantHeader(label = stringResource(R.string.screen_progress_label))
            Text(text = stringResource(R.string.figma_progress_title), style = MaterialTheme.typography.headlineLarge, color = TextPrimary)
            Text(text = stringResource(R.string.figma_progress_subtitle), style = MaterialTheme.typography.bodyLarge, color = TextSecondary)
            Row(modifier = Modifier.fillMaxWidth().background(color = Selected, MaterialTheme.shapes.large).padding(Dimens.Space16), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(Dimens.Space12)) {
                    Text(text = state.gradeLabel.uppercase(), style = MaterialTheme.typography.labelSmall, color = Primary)
                    Text(text = stringResource(R.string.figma_progress_count, state.passedCount, state.totalCount), style = MaterialTheme.typography.headlineMedium, color = TextPrimary)
                    Text(text = stringResource(R.string.figma_progress_subject, state.subjectName, state.percent), style = MaterialTheme.typography.bodyLarge, color = TextSecondary)
                }
                Image(painter = painterResource(R.drawable.figma_chart), contentDescription = null)
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(Dimens.Space12)) {
                ProgressStat(value = state.availableCount.toString(), label = stringResource(R.string.figma_progress_available), modifier = Modifier.weight(1f), warm = true)
                ProgressStat(value = state.lockedCount.toString(), label = stringResource(R.string.figma_progress_locked), modifier = Modifier.weight(1f), warm = false)
            }
            Column(modifier = Modifier.fillMaxWidth().background(color = Surface, MaterialTheme.shapes.large).padding(Dimens.Space16), verticalArrangement = Arrangement.spacedBy(Dimens.Space8)) {
                Text(text = stringResource(R.string.figma_progress_how), style = MaterialTheme.typography.titleLarge, color = TextPrimary)
                Text(text = stringResource(R.string.figma_progress_how_text), style = MaterialTheme.typography.bodyLarge, color = TextSecondary)
            }
            Text(text = stringResource(R.string.figma_progress_topics), style = MaterialTheme.typography.titleLarge, color = Primary, modifier = Modifier.fillMaxWidth().background(color = Selected, MaterialTheme.shapes.large).clickable(onClick = onTopics).padding(Dimens.Space16))
            Column(modifier = Modifier.fillMaxWidth().background(color = AttentionContainer, MaterialTheme.shapes.large).padding(Dimens.Space16), verticalArrangement = Arrangement.spacedBy(Dimens.Space8)) {
                Text(text = stringResource(R.string.figma_progress_note), style = MaterialTheme.typography.titleLarge, color = TextPrimary)
                Text(text = stringResource(R.string.figma_progress_note_text), style = MaterialTheme.typography.bodyLarge, color = TextSecondary)
            }
            Spacer(modifier = Modifier.height(Dimens.Space8))
        }
        AppNavigationBar(items = listOf(NavItem(stringResource(R.string.home_nav_home), R.drawable.ic_nav_home), NavItem(stringResource(R.string.home_nav_subjects), R.drawable.ic_nav_grid), NavItem(stringResource(R.string.nav_progress), R.drawable.ic_nav_progress)), selectedIndex = 2, onSelect = { index -> when (index) { 0 -> onHome(); 1 -> onSubjects() } })
    }
}

@Composable
private fun ProgressStat(value: String, label: String, modifier: Modifier, warm: Boolean) {
    Column(modifier = modifier.background(color = if (warm) AttentionContainer else Surface, MaterialTheme.shapes.large).padding(Dimens.Space16), verticalArrangement = Arrangement.spacedBy(Dimens.Space8)) {
        Text(text = value, style = MaterialTheme.typography.headlineMedium, color = Primary)
        Text(text = label, style = MaterialTheme.typography.bodyLarge, color = TextSecondary)
    }
}


