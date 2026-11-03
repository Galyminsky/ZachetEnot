package com.galyaminsky.zachetenot.ui.screens.classselection

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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.galyaminsky.zachetenot.R
import com.galyaminsky.zachetenot.domain.catalog.AppCatalog
import com.galyaminsky.zachetenot.ui.components.ClassCard
import com.galyaminsky.zachetenot.ui.components.SelectionState
import com.galyaminsky.zachetenot.ui.components.VariantHeader
import com.galyaminsky.zachetenot.ui.theme.AttentionContainer
import com.galyaminsky.zachetenot.ui.theme.InfoContainer
import com.galyaminsky.zachetenot.ui.theme.Background
import com.galyaminsky.zachetenot.ui.theme.Dimens
import com.galyaminsky.zachetenot.ui.theme.Primary
import com.galyaminsky.zachetenot.ui.theme.TextPrimary
import com.galyaminsky.zachetenot.ui.theme.TextSecondary
import com.galyaminsky.zachetenot.ui.theme.ClassHeadline
import com.galyaminsky.zachetenot.ui.theme.ClassSubtitle
import com.galyaminsky.zachetenot.ui.theme.HintSymbol
import com.galyaminsky.zachetenot.ui.theme.ClassHintText
import com.galyaminsky.zachetenot.ui.theme.CardShape20

@Composable
fun ClassSelectionScreen(selectedGrade: Int?, onGradeSelected: (Int) -> Unit, onContinue: () -> Unit, modifier: Modifier = Modifier) {
    val illustrations = listOf(R.drawable.illustration_locked, R.drawable.illustration_locked_2, R.drawable.illustration_school, R.drawable.illustration_school_locked)
    Column(modifier = modifier.fillMaxSize().background(color = Background).verticalScroll(rememberScrollState()).padding(horizontal = Dimens.ScreenHorizontalPadding)) {
        Spacer(modifier = Modifier.height(Dimens.Space24))
        VariantHeader(label = stringResource(R.string.screen_class_label))
        Spacer(modifier = Modifier.height(Dimens.Space16))
        Text(text = stringResource(R.string.figma_class_title), style = ClassHeadline, color = TextPrimary)
        Spacer(modifier = Modifier.height(Dimens.Space8))
        Text(text = stringResource(R.string.figma_class_subtitle), style = ClassSubtitle, color = TextSecondary)
        Spacer(modifier = Modifier.height(Dimens.Space20))
        AppCatalog.grades.chunked(2).forEachIndexed { rowIndex, grades ->
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(Dimens.Space12)) {
                grades.forEachIndexed { columnIndex, grade ->
                    ClassCard(grade = grade.number, state = when { !grade.isAvailable -> SelectionState.Locked; grade.number == selectedGrade -> SelectionState.Selected; else -> SelectionState.Default },
                        illustration = illustrations[rowIndex * 2 + columnIndex], onClick = { onGradeSelected(grade.number); onContinue() }, modifier = Modifier.weight(1f))
                }
            }
            Spacer(modifier = Modifier.height(if (rowIndex == 0) Dimens.Space12 else Dimens.Space20))
        }
        Row(modifier = Modifier.fillMaxWidth().background(color = InfoContainer, shape = MaterialTheme.shapes.medium).padding(Dimens.ClassCardPadding), horizontalArrangement = Arrangement.spacedBy(Dimens.Space8)) {
            Text(text = stringResource(R.string.variant_sparkle), style = HintSymbol, color = Primary)
            Text(text = stringResource(R.string.variant_class_hint), style = ClassHintText, color = TextPrimary)
        }
    }
}

