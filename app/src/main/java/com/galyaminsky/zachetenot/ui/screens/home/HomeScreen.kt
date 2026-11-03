package com.galyaminsky.zachetenot.ui.screens.home

import androidx.annotation.DrawableRes
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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
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
import com.galyaminsky.zachetenot.ui.components.StepProgressBar
import com.galyaminsky.zachetenot.ui.components.VariantHeader
import com.galyaminsky.zachetenot.ui.components.AppLogo
import com.galyaminsky.zachetenot.ui.theme.AttentionContainer
import com.galyaminsky.zachetenot.ui.theme.Background
import com.galyaminsky.zachetenot.ui.theme.Dimens
import com.galyaminsky.zachetenot.ui.theme.Disabled
import com.galyaminsky.zachetenot.ui.theme.InfoContainer
import com.galyaminsky.zachetenot.ui.theme.Primary
import com.galyaminsky.zachetenot.ui.theme.Selected
import com.galyaminsky.zachetenot.ui.theme.Surface
import com.galyaminsky.zachetenot.ui.theme.TextPrimary
import com.galyaminsky.zachetenot.ui.theme.TextSecondary
import com.galyaminsky.zachetenot.ui.theme.CardShape20
import com.galyaminsky.zachetenot.ui.theme.HeroShape22
import com.galyaminsky.zachetenot.ui.theme.ProgressCounter
import com.galyaminsky.zachetenot.ui.theme.ProgressPercent
import com.galyaminsky.zachetenot.ui.theme.CardSymbol
import com.galyaminsky.zachetenot.ui.theme.FactSymbol

data class HomeSubjectUi(val id: String, val title: String, val subtitle: String, val available: Boolean, val selected: Boolean, val iconText: String? = null, @DrawableRes val icon: Int? = null)
data class HomeUiState(val gradeLabel: String, val greeting: String, val programSummary: String, val ctaLabel: String, val subjects: List<HomeSubjectUi>, val passedCount: Int, val totalCount: Int, val percent: Int, val progressMessage: String)

@Composable
fun HomeScreen(state: HomeUiState, onSettingsClick: () -> Unit, onProgramClick: () -> Unit, onSubjectClick: (String) -> Unit, onProgressClick: () -> Unit, modifier: Modifier = Modifier, onChangeGradeClick: () -> Unit = {}, onSubjectsClick: () -> Unit = onProgramClick, onNextTopicClick: () -> Unit = onProgramClick) {
    Column(modifier = modifier.fillMaxSize().background(color = Background)) {
        Column(modifier = Modifier.weight(1f).verticalScroll(rememberScrollState()).padding(horizontal = Dimens.ScreenHorizontalPadding), verticalArrangement = Arrangement.spacedBy(Dimens.Space15)) {
            Spacer(modifier = Modifier.height(Dimens.Space8))
            VariantHeader(label = stringResource(R.string.screen_home_label), onLabelClick = onSettingsClick, actionLabel = stringResource(R.string.nav_settings))
            Text(text = stringResource(R.string.variant_home_title), style = MaterialTheme.typography.headlineLarge, color = TextPrimary)
            Text(text = stringResource(R.string.variant_home_subtitle), style = MaterialTheme.typography.bodyLarge, color = TextSecondary)
            Row(modifier = Modifier.fillMaxWidth().heightIn(min = Dimens.HomeHeroHeight).background(color = Selected, shape = HeroShape22).clickable(onClick = onNextTopicClick).padding(Dimens.Space16), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(Dimens.Space8)) {
                    Text(text = stringResource(R.string.variant_home_context), style = MaterialTheme.typography.labelSmall, color = Primary)
                    Text(text = stringResource(R.string.variant_home_hero), style = MaterialTheme.typography.headlineMedium, color = TextPrimary)
                    Text(text = stringResource(R.string.variant_home_hero_note), style = MaterialTheme.typography.bodyLarge, color = TextSecondary)
                }
                Image(painter = painterResource(R.drawable.figma_rocket), contentDescription = null, modifier = Modifier.size(width = Dimens.HeroIllustrationWidth, height = Dimens.HeroIllustrationHeight))
            }
            Row(modifier = Modifier.fillMaxWidth().heightIn(min = Dimens.HomeActionHeight).background(color = Primary, shape = CardShape20).clickable(onClick = onProgramClick).padding(Dimens.Space15), horizontalArrangement = Arrangement.spacedBy(Dimens.Space12), verticalAlignment = Alignment.CenterVertically) {
                Text(text = stringResource(R.string.variant_sparkle), style = CardSymbol, color = Surface)
                Column(verticalArrangement = Arrangement.spacedBy(Dimens.Space4)) {
                    Text(text = stringResource(R.string.home_choose_subject), style = MaterialTheme.typography.titleLarge, color = Surface)
                    Text(text = stringResource(R.string.variant_home_subject_note), style = MaterialTheme.typography.bodyLarge, color = Surface)
                }
            }
            Row(modifier = Modifier.fillMaxWidth().heightIn(min = Dimens.HomeFactHeight).background(color = InfoContainer, shape = CardShape20).padding(Dimens.Space16), horizontalArrangement = Arrangement.spacedBy(Dimens.Space8), verticalAlignment = Alignment.CenterVertically) {
                Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(Dimens.Space8)) {
                    Text(text = stringResource(R.string.variant_fact_label), style = MaterialTheme.typography.labelSmall, color = Primary)
                    Text(text = stringResource(R.string.variant_fact_title), style = MaterialTheme.typography.titleLarge, color = TextPrimary)
                    Text(text = stringResource(R.string.variant_fact_note), style = MaterialTheme.typography.bodySmall, color = TextSecondary)
                }
                Text(text = stringResource(R.string.variant_sparkle), style = FactSymbol, color = Primary)
            }
            Column(modifier = Modifier.fillMaxWidth().heightIn(min = Dimens.HomeProgressHeight).background(color = Surface, shape = CardShape20).clickable(onClick = onProgressClick).padding(Dimens.Space16), verticalArrangement = Arrangement.spacedBy(Dimens.Space8)) {
                Text(text = stringResource(R.string.variant_home_success), style = MaterialTheme.typography.titleLarge, color = TextPrimary)
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(text = stringResource(R.string.figma_progress_count, state.passedCount, state.totalCount), style = ProgressCounter, color = Primary)
                    Text(text = stringResource(R.string.percent_format, state.percent), style = ProgressPercent, color = TextSecondary)
                }
                StepProgressBar(progress = state.percent / 100f, trackColor = Disabled, fillColor = Primary)
            }
            Spacer(modifier = Modifier.height(Dimens.Space8))
        }
        AppNavigationBar(items = listOf(NavItem(stringResource(R.string.home_nav_home), R.drawable.ic_nav_home), NavItem(stringResource(R.string.home_nav_subjects), R.drawable.ic_nav_grid), NavItem(stringResource(R.string.nav_progress), R.drawable.ic_nav_progress)), selectedIndex = 0, onSelect = { index -> when (index) { 1 -> onSubjectsClick(); 2 -> onProgressClick() } })
    }
}


