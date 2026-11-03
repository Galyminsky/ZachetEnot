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
import com.galyaminsky.zachetenot.ui.components.VariantHeader
import com.galyaminsky.zachetenot.ui.theme.AttentionContainer
import com.galyaminsky.zachetenot.ui.theme.Background
import com.galyaminsky.zachetenot.ui.theme.Dimens
import com.galyaminsky.zachetenot.ui.theme.InfoContainer
import com.galyaminsky.zachetenot.ui.theme.Primary
import com.galyaminsky.zachetenot.ui.theme.PositiveInfoContainer
import com.galyaminsky.zachetenot.ui.theme.Surface
import com.galyaminsky.zachetenot.ui.theme.TextPrimary
import com.galyaminsky.zachetenot.ui.theme.TextSecondary

@Composable
fun ReviewScreen(state: ReviewUiState, onBack: () -> Unit, onList: () -> Unit, modifier: Modifier = Modifier) {
    if (state.mistakes.isEmpty()) {
        Column(modifier = modifier.fillMaxSize().background(color = Background).padding(horizontal = Dimens.ScreenHorizontalPadding), verticalArrangement = Arrangement.spacedBy(Dimens.Space16)) {
            Spacer(modifier = Modifier.height(Dimens.Space8))
            VariantHeader(label = stringResource(R.string.screen_review_label))
            Column(modifier = Modifier.fillMaxWidth().background(color = PositiveInfoContainer, MaterialTheme.shapes.large).padding(Dimens.Space24), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(Dimens.Space16)) {
                Image(painter = painterResource(R.drawable.figma_cup), contentDescription = null)
                Text(text = stringResource(R.string.variant_review_perfect_badge), style = MaterialTheme.typography.labelSmall, color = Primary, modifier = Modifier.background(color = Surface, MaterialTheme.shapes.medium).padding(Dimens.Space8))
                Text(text = stringResource(R.string.variant_review_perfect_title), style = MaterialTheme.typography.headlineLarge, color = TextPrimary)
                Text(text = state.statsLine, style = MaterialTheme.typography.bodyLarge, color = TextSecondary)
            }
            Text(text = stringResource(R.string.variant_review_perfect_info), style = MaterialTheme.typography.bodyLarge, color = TextSecondary, modifier = Modifier.fillMaxWidth().background(color = Surface, MaterialTheme.shapes.large).padding(Dimens.Space16))
            Spacer(modifier = Modifier.weight(1f))
            AppButton(text = stringResource(R.string.figma_result_topics), onClick = onList, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(Dimens.Space8))
        }
        return
    }
    Column(modifier = modifier.fillMaxSize().background(color = Background).verticalScroll(rememberScrollState()).padding(horizontal = Dimens.ScreenHorizontalPadding), verticalArrangement = Arrangement.spacedBy(Dimens.Space16)) {
        Spacer(modifier = Modifier.height(Dimens.Space8))
        VariantHeader(label = stringResource(R.string.screen_review_label))
        Text(text = stringResource(R.string.variant_review_title), style = MaterialTheme.typography.headlineLarge, color = TextPrimary)
        Text(text = stringResource(R.string.variant_review_subtitle), style = MaterialTheme.typography.bodyLarge, color = TextSecondary)
        Row(modifier = Modifier.fillMaxWidth().background(color = AttentionContainer, MaterialTheme.shapes.large).padding(Dimens.Space16), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(Dimens.Space12)) {
                Text(text = state.statsLine, style = MaterialTheme.typography.labelSmall, color = Primary)
                Text(text = stringResource(R.string.variant_review_one), style = MaterialTheme.typography.headlineMedium, color = TextPrimary)
                Text(text = stringResource(R.string.variant_review_together), style = MaterialTheme.typography.bodyLarge, color = TextSecondary)
            }
            Image(painter = painterResource(R.drawable.figma_bulb), contentDescription = null)
        }
        state.mistakes.forEach { mistake ->
            Column(modifier = Modifier.fillMaxWidth().background(color = Surface, MaterialTheme.shapes.large).padding(Dimens.Space16), verticalArrangement = Arrangement.spacedBy(Dimens.Space12)) {
                Text(text = mistake.questionLabel, style = MaterialTheme.typography.labelSmall, color = Primary)
                Text(text = mistake.ruleTitle, style = MaterialTheme.typography.titleLarge, color = TextPrimary)
                Text(text = stringResource(R.string.variant_review_rule), style = MaterialTheme.typography.titleLarge, color = Primary)
                Text(text = mistake.ruleText, style = MaterialTheme.typography.bodyLarge, color = TextPrimary)
            }
        }
        AppButton(text = stringResource(R.string.figma_result_topics), onClick = onList, modifier = Modifier.fillMaxWidth())
        Column(modifier = Modifier.fillMaxWidth().background(color = InfoContainer, MaterialTheme.shapes.large).padding(Dimens.Space16), verticalArrangement = Arrangement.spacedBy(Dimens.Space8)) {
            Text(text = stringResource(R.string.variant_review_retry), style = MaterialTheme.typography.titleLarge, color = TextPrimary)
            Text(text = stringResource(R.string.variant_review_retry_note), style = MaterialTheme.typography.bodyLarge, color = TextSecondary)
        }
    }
}

