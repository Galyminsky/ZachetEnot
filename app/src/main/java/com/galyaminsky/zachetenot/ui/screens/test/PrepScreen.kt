package com.galyaminsky.zachetenot.ui.screens.test

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
import com.galyaminsky.zachetenot.ui.components.AppButton
import com.galyaminsky.zachetenot.ui.components.VariantHeader
import com.galyaminsky.zachetenot.ui.theme.AttentionContainer
import com.galyaminsky.zachetenot.ui.theme.Background
import com.galyaminsky.zachetenot.ui.theme.Dimens
import com.galyaminsky.zachetenot.ui.theme.Primary
import com.galyaminsky.zachetenot.ui.theme.Selected
import com.galyaminsky.zachetenot.ui.theme.Surface
import com.galyaminsky.zachetenot.ui.theme.TextPrimary
import com.galyaminsky.zachetenot.ui.theme.TextSecondary
import com.galyaminsky.zachetenot.ui.theme.HeroShape22
import com.galyaminsky.zachetenot.ui.theme.PrepStatValue
import com.galyaminsky.zachetenot.ui.theme.PrimarySoftBorder
import com.galyaminsky.zachetenot.ui.theme.Border
import androidx.compose.foundation.border

@Composable
fun PrepScreen(state: PrepUiState, onBack: () -> Unit, onStart: () -> Unit, modifier: Modifier = Modifier, onHome: () -> Unit = onBack, onProgress: () -> Unit = onBack) {
    Column(modifier = modifier.fillMaxSize().background(color = Background).verticalScroll(rememberScrollState()).padding(horizontal = Dimens.ScreenHorizontalPadding), verticalArrangement = Arrangement.spacedBy(Dimens.Space15)) {
        Spacer(modifier = Modifier.height(Dimens.Space8))
        VariantHeader(label = stringResource(R.string.screen_prep_label))
        Text(text = state.title, style = MaterialTheme.typography.headlineLarge, color = TextPrimary)
        Text(text = state.subtitle, style = MaterialTheme.typography.bodyLarge, color = TextSecondary)
        Row(modifier = Modifier.fillMaxWidth().background(color = Selected, shape = HeroShape22).padding(Dimens.Space16), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(Dimens.Space8)) {
                Text(text = stringResource(R.string.variant_prep_subject), style = MaterialTheme.typography.labelSmall, color = Primary)
                Text(text = state.conditionsTitle, style = MaterialTheme.typography.headlineMedium, color = TextPrimary)
                Text(text = state.conditionsStats, style = MaterialTheme.typography.bodyLarge, color = TextSecondary)
            }
            Image(painter = painterResource(R.drawable.figma_rocket), contentDescription = null, modifier = Modifier.size(width = Dimens.HeroIllustrationWidth, height = Dimens.HeroIllustrationHeight))
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(Dimens.Space8)) {
            PrepStat(value = stringResource(R.string.figma_prep_count), label = stringResource(R.string.variant_prep_questions), modifier = Modifier.weight(1f))
            PrepStat(value = stringResource(R.string.figma_prep_minutes_count), label = stringResource(R.string.variant_prep_minutes), modifier = Modifier.weight(1f))
            PrepStat(value = stringResource(R.string.figma_prep_pass_count), label = stringResource(R.string.variant_prep_pass), modifier = Modifier.weight(1f))
        }
        Column(modifier = Modifier.fillMaxWidth().background(color = AttentionContainer, shape = MaterialTheme.shapes.medium).padding(Dimens.Space16), verticalArrangement = Arrangement.spacedBy(Dimens.Space8)) {
            Text(text = state.warningTitle, style = MaterialTheme.typography.titleLarge, color = TextPrimary)
            Text(text = state.warningText, style = MaterialTheme.typography.bodyLarge, color = TextPrimary)
        }
        AppButton(text = state.buttonText, onClick = onStart, modifier = Modifier.fillMaxWidth(), height = Dimens.PrepButtonHeight)
        Row(modifier = Modifier.fillMaxWidth().background(color = Surface, shape = MaterialTheme.shapes.medium).padding(Dimens.Space16), horizontalArrangement = Arrangement.spacedBy(Dimens.Space12), verticalAlignment = Alignment.CenterVertically) {
            Icon(painter = painterResource(R.drawable.ic_repeat), contentDescription = null, modifier = Modifier.size(Dimens.Space24), tint = Primary)
            Text(text = state.caption, style = MaterialTheme.typography.bodyLarge, color = TextPrimary)
        }
    }
}

@Composable
private fun PrepStat(value: String, label: String, modifier: Modifier = Modifier) {
    Column(modifier = modifier.height(Dimens.PrepStatHeight).background(color = Surface, shape = MaterialTheme.shapes.medium).border(width = Dimens.BorderWidth, color = PrimarySoftBorder, shape = MaterialTheme.shapes.medium).padding(Dimens.PrepStatPadding), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        Text(text = value, style = PrepStatValue, color = Primary)
        Text(text = label, style = MaterialTheme.typography.bodySmall, color = TextSecondary)
    }
}

