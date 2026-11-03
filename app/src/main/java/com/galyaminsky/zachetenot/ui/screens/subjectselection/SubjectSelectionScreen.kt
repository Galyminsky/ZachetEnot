package com.galyaminsky.zachetenot.ui.screens.subjectselection

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import com.galyaminsky.zachetenot.ui.theme.Background
import com.galyaminsky.zachetenot.ui.theme.Dimens
import com.galyaminsky.zachetenot.ui.theme.Disabled
import com.galyaminsky.zachetenot.ui.theme.Primary
import com.galyaminsky.zachetenot.ui.theme.Selected
import com.galyaminsky.zachetenot.ui.theme.Surface
import com.galyaminsky.zachetenot.ui.theme.TextPrimary
import com.galyaminsky.zachetenot.ui.theme.TextSecondary
import com.galyaminsky.zachetenot.ui.theme.CapsuleShape
import com.galyaminsky.zachetenot.ui.theme.CardShape20
import com.galyaminsky.zachetenot.ui.theme.HeroShape22
import com.galyaminsky.zachetenot.ui.theme.FutureSubjectTitle

@Composable
fun SubjectSelectionScreen(selectedSubjectId: String?, onSubjectSelected: (String) -> Unit, onBack: () -> Unit, onContinue: () -> Unit, modifier: Modifier = Modifier, onProgress: () -> Unit = {}, onUnavailable: () -> Unit = {}) {
    Column(modifier = modifier.fillMaxSize().background(color = Background)) {
        Column(modifier = Modifier.weight(1f).verticalScroll(rememberScrollState()).padding(horizontal = Dimens.ScreenHorizontalPadding), verticalArrangement = Arrangement.spacedBy(Dimens.Space15)) {
            Spacer(modifier = Modifier.height(Dimens.Space8))
            VariantHeader(label = stringResource(R.string.screen_subject_label))
            Text(text = stringResource(R.string.subject_selection_title), style = MaterialTheme.typography.headlineLarge, color = TextPrimary)
            Text(text = stringResource(R.string.variant_subject_subtitle), style = MaterialTheme.typography.bodyLarge, color = TextSecondary)
            Row(modifier = Modifier.fillMaxWidth().background(color = Selected, shape = HeroShape22).clickable { onSubjectSelected("russian"); onContinue() }.padding(Dimens.Space16), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(Dimens.Space8)) {
                    Text(text = stringResource(R.string.variant_subject_available), style = MaterialTheme.typography.labelSmall, color = Primary)
                    Text(text = stringResource(R.string.subject_russian), style = MaterialTheme.typography.headlineMedium, color = TextPrimary)
                    Text(text = stringResource(R.string.variant_subject_russian_note), style = MaterialTheme.typography.bodyLarge, color = TextSecondary)
                }
                Image(painter = painterResource(R.drawable.figma_book), contentDescription = null, modifier = Modifier.size(width = Dimens.HeroIllustrationWidth, height = Dimens.HeroIllustrationHeight))
            }
            Column(modifier = Modifier.fillMaxWidth().background(color = Surface, shape = CardShape20).clickable(onClick = onUnavailable).padding(Dimens.Space18), verticalArrangement = Arrangement.spacedBy(Dimens.Space12)) {
                Text(text = stringResource(R.string.variant_subject_soon), style = MaterialTheme.typography.labelSmall, color = TextSecondary, modifier = Modifier.background(color = Disabled, MaterialTheme.shapes.small).padding(Dimens.Space8))
                Text(text = stringResource(R.string.variant_subject_other), style = FutureSubjectTitle, color = TextPrimary)
                Text(text = stringResource(R.string.variant_subject_other_note), style = MaterialTheme.typography.bodyLarge, color = TextSecondary)
            }
            Image(painter = painterResource(R.drawable.figma_pencil), contentDescription = null, modifier = Modifier.align(Alignment.CenterHorizontally).size(width = Dimens.DecorativePencilWidth, height = Dimens.DecorativePencilHeight))
            Column(modifier = Modifier.fillMaxWidth().background(color = Surface, shape = CardShape20).padding(Dimens.Space16), verticalArrangement = Arrangement.spacedBy(Dimens.Space16)) {
                Text(text = stringResource(R.string.variant_subject_later), style = MaterialTheme.typography.titleLarge, color = TextPrimary)
                Row(horizontalArrangement = Arrangement.spacedBy(Dimens.Space12), verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.size(Dimens.Space12).border(width = Dimens.BorderWidthSelected, color = TextSecondary, shape = CapsuleShape))
                    Text(text = stringResource(R.string.subject_math), style = MaterialTheme.typography.bodyLarge, color = TextSecondary)
                }
                Row(horizontalArrangement = Arrangement.spacedBy(Dimens.Space12), verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.size(Dimens.Space12).border(width = Dimens.BorderWidthSelected, color = TextSecondary, shape = CapsuleShape))
                    Text(text = stringResource(R.string.subject_literature), style = MaterialTheme.typography.bodyLarge, color = TextSecondary)
                }
            }
            Spacer(modifier = Modifier.height(Dimens.Space8))
        }
        AppNavigationBar(items = listOf(NavItem(stringResource(R.string.home_nav_home), R.drawable.ic_nav_home), NavItem(stringResource(R.string.home_nav_subjects), R.drawable.ic_nav_grid), NavItem(stringResource(R.string.nav_progress), R.drawable.ic_nav_progress)), selectedIndex = 1, onSelect = { when (it) { 0 -> onBack(); 2 -> onProgress() } })
    }
}


