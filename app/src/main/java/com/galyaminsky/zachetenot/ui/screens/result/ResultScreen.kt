package com.galyaminsky.zachetenot.ui.screens.result

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
import com.galyaminsky.zachetenot.ui.components.VariantHeader
import com.galyaminsky.zachetenot.ui.theme.Background
import com.galyaminsky.zachetenot.ui.theme.AttentionContainer
import com.galyaminsky.zachetenot.ui.theme.Dimens
import com.galyaminsky.zachetenot.ui.theme.Primary
import com.galyaminsky.zachetenot.ui.theme.Selected
import com.galyaminsky.zachetenot.ui.theme.Success
import com.galyaminsky.zachetenot.ui.theme.SuccessContainer
import com.galyaminsky.zachetenot.ui.theme.TextPrimary
import com.galyaminsky.zachetenot.ui.theme.TextSecondary

@Composable
fun ResultScreen(state: ResultUiState, onReview: () -> Unit, onRetry: () -> Unit, onList: () -> Unit, modifier: Modifier = Modifier, reviewAvailable: Boolean = true, onHome: () -> Unit = onList, onProgress: () -> Unit = onList) {
    if (!state.passed) {
        Column(modifier = modifier.fillMaxSize().background(color = Background).padding(horizontal = Dimens.ScreenHorizontalPadding), verticalArrangement = Arrangement.spacedBy(Dimens.Space16)) {
            Spacer(modifier = Modifier.height(Dimens.Space8))
            VariantHeader(label = stringResource(R.string.variant_failed_status))
            Column(modifier = Modifier.fillMaxWidth().background(color = AttentionContainer, MaterialTheme.shapes.large).padding(Dimens.Space24), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(Dimens.Space16)) {
                Image(painter = painterResource(R.drawable.figma_result_retry), contentDescription = null)
                Text(text = stringResource(R.string.variant_failed_badge), style = MaterialTheme.typography.labelSmall, color = Primary, modifier = Modifier.background(color = com.galyaminsky.zachetenot.ui.theme.Surface, MaterialTheme.shapes.medium).padding(Dimens.Space8))
                Text(text = stringResource(R.string.variant_failed_title), style = MaterialTheme.typography.headlineLarge, color = TextPrimary)
                Text(text = state.failedNote ?: state.message, style = MaterialTheme.typography.bodyLarge, color = TextSecondary)
            }
            Row(modifier = Modifier.fillMaxWidth().background(color = com.galyaminsky.zachetenot.ui.theme.Surface, MaterialTheme.shapes.large).padding(Dimens.Space16), horizontalArrangement = Arrangement.spacedBy(Dimens.Space12)) {
                Text(text = stringResource(R.string.variant_failed_unlimited), style = MaterialTheme.typography.bodyLarge, color = TextSecondary)
            }
            Spacer(modifier = Modifier.weight(1f))
            if (reviewAvailable) AppButton(text = stringResource(R.string.variant_result_review), onClick = onReview, modifier = Modifier.fillMaxWidth())
            AppButton(text = state.retryButton, onClick = onRetry, modifier = Modifier.fillMaxWidth(), style = AppButtonStyle.Secondary)
            Spacer(modifier = Modifier.height(Dimens.Space8))
        }
        return
    }
    Column(modifier = modifier.fillMaxSize().background(color = Background)) {
        Column(modifier = Modifier.weight(1f).verticalScroll(rememberScrollState()).padding(horizontal = Dimens.ScreenHorizontalPadding), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(Dimens.Space16)) {
            Spacer(modifier = Modifier.height(Dimens.Space8))
            VariantHeader(label = stringResource(R.string.screen_result_label))
            Spacer(modifier = Modifier.height(Dimens.Space16))
            Image(painter = painterResource(R.drawable.figma_cup), contentDescription = null)
            Text(text = stringResource(R.string.variant_result_passed), style = MaterialTheme.typography.labelSmall, color = Success, modifier = Modifier.background(color = SuccessContainer, MaterialTheme.shapes.medium).padding(Dimens.Space8))
            Text(text = state.verdictTitle, style = MaterialTheme.typography.headlineLarge, color = TextPrimary)
            Column(modifier = Modifier.fillMaxWidth().background(color = Selected, MaterialTheme.shapes.large).padding(Dimens.Space24), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(Dimens.Space8)) {
                Text(text = state.correctText, style = MaterialTheme.typography.displayLarge, color = Primary)
                Text(text = state.correctLabel, style = MaterialTheme.typography.bodyLarge, color = TextPrimary)
                Text(text = stringResource(R.string.variant_result_mastered), style = MaterialTheme.typography.bodyLarge, color = TextSecondary)
            }
            if (reviewAvailable) AppButton(text = stringResource(R.string.variant_result_review), onClick = onReview, modifier = Modifier.fillMaxWidth())
            AppButton(text = stringResource(R.string.figma_result_topics), onClick = onList, modifier = Modifier.fillMaxWidth(), style = AppButtonStyle.Secondary)
            Column(modifier = Modifier.fillMaxWidth().background(color = SuccessContainer, MaterialTheme.shapes.large).padding(Dimens.Space16), verticalArrangement = Arrangement.spacedBy(Dimens.Space8)) {
                Text(text = stringResource(R.string.variant_result_topic_passed), style = MaterialTheme.typography.titleLarge, color = Success)
                Text(text = stringResource(R.string.variant_result_next), style = MaterialTheme.typography.bodyLarge, color = TextPrimary)
            }
            Spacer(modifier = Modifier.height(Dimens.Space8))
        }
        AppNavigationBar(items = listOf(NavItem(stringResource(R.string.home_nav_home), R.drawable.ic_nav_home), NavItem(stringResource(R.string.home_nav_subjects), R.drawable.ic_nav_grid), NavItem(stringResource(R.string.nav_progress), R.drawable.ic_nav_progress)), selectedIndex = 1, onSelect = { index -> when (index) { 0 -> onHome(); 1 -> onList(); 2 -> onProgress() } })
    }
}


