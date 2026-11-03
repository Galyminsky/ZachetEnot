package com.galyaminsky.zachetenot.ui.screens.progress

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import com.galyaminsky.zachetenot.ui.components.AppButton
import com.galyaminsky.zachetenot.ui.components.AppButtonStyle
import com.galyaminsky.zachetenot.ui.components.AppNavigationBar
import com.galyaminsky.zachetenot.ui.components.NavItem
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
    Column(modifier = modifier.fillMaxSize().background(Background)) {
        Column(modifier = Modifier.weight(1f).verticalScroll(rememberScrollState()).padding(horizontal = Dimens.ScreenHorizontalPadding)) {
            Spacer(modifier = Modifier.height(Dimens.Space16))
            Text(text = stringResource(R.string.figma_progress_title), style = MaterialTheme.typography.headlineMedium, color = TextPrimary)
            Text(text = stringResource(R.string.figma_progress_subtitle), style = MaterialTheme.typography.bodyLarge, color = TextSecondary)
            Spacer(modifier = Modifier.height(Dimens.Space16))
            Row(modifier = Modifier.fillMaxWidth().background(Selected, MaterialTheme.shapes.large).padding(Dimens.Space16), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Column(verticalArrangement = Arrangement.spacedBy(Dimens.Space8)) {
                    Text(text = state.gradeLabel, style = MaterialTheme.typography.labelSmall, color = Primary)
                    Text(text = stringResource(R.string.figma_progress_count, state.passedCount, state.totalCount), style = MaterialTheme.typography.titleLarge, color = TextPrimary)
                    Text(text = stringResource(R.string.figma_progress_subject, state.subjectName, state.percent), style = MaterialTheme.typography.bodySmall, color = TextSecondary)
                }
                Image(painter = painterResource(R.drawable.figma_chart), contentDescription = null)
            }
            Spacer(modifier = Modifier.height(Dimens.Space16))
            Row(horizontalArrangement = Arrangement.spacedBy(Dimens.Space12)) {
                Column(modifier = Modifier.weight(1f).background(AttentionContainer, MaterialTheme.shapes.medium).padding(Dimens.Space16)) {
                    Text(text = state.availableCount.toString(), style = MaterialTheme.typography.headlineMedium, color = Primary)
                    Text(text = stringResource(R.string.figma_progress_available), style = MaterialTheme.typography.bodySmall, color = TextSecondary)
                }
                Column(modifier = Modifier.weight(1f).background(Surface, MaterialTheme.shapes.medium).padding(Dimens.Space16)) {
                    Text(text = state.lockedCount.toString(), style = MaterialTheme.typography.headlineMedium, color = Primary)
                    Text(text = stringResource(R.string.figma_progress_locked), style = MaterialTheme.typography.bodySmall, color = TextSecondary)
                }
            }
            Spacer(modifier = Modifier.height(Dimens.Space16))
            Column(modifier = Modifier.fillMaxWidth().background(Surface, MaterialTheme.shapes.large).padding(Dimens.Space16), verticalArrangement = Arrangement.spacedBy(Dimens.Space8)) {
                Text(text = stringResource(R.string.figma_progress_how), style = MaterialTheme.typography.titleLarge, color = TextPrimary)
                Text(text = stringResource(R.string.figma_progress_how_text), style = MaterialTheme.typography.bodyLarge, color = TextSecondary)
            }
            Spacer(modifier = Modifier.height(Dimens.Space12))
            AppButton(text = stringResource(R.string.figma_progress_topics), onClick = onTopics, modifier = Modifier.fillMaxWidth(), style = AppButtonStyle.Secondary)
            Spacer(modifier = Modifier.height(Dimens.Space12))
            Column(modifier = Modifier.fillMaxWidth().background(AttentionContainer, MaterialTheme.shapes.large).padding(Dimens.Space16), verticalArrangement = Arrangement.spacedBy(Dimens.Space8)) {
                Text(text = stringResource(R.string.figma_progress_note), style = MaterialTheme.typography.titleLarge, color = TextPrimary)
                Text(text = stringResource(R.string.figma_progress_note_text), style = MaterialTheme.typography.bodySmall, color = TextSecondary)
            }
            Spacer(modifier = Modifier.height(Dimens.Space16))
        }
        AppNavigationBar(items = listOf(NavItem(stringResource(R.string.home_nav_home), R.drawable.ic_logo), NavItem(stringResource(R.string.home_nav_subjects), R.drawable.ic_layers), NavItem(stringResource(R.string.nav_progress), R.drawable.ic_chart)), selectedIndex = 2, onSelect = { index -> when (index) { 0 -> onHome(); 1 -> onSubjects() } })
    }
}
