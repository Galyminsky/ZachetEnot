package com.galyaminsky.zachetenot.ui.screens.subjectselection

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.galyaminsky.zachetenot.R
import com.galyaminsky.zachetenot.domain.catalog.AppCatalog
import com.galyaminsky.zachetenot.ui.components.AppButton
import com.galyaminsky.zachetenot.ui.components.BackButton
import com.galyaminsky.zachetenot.ui.components.HintRow
import com.galyaminsky.zachetenot.ui.components.InfoChip
import com.galyaminsky.zachetenot.ui.components.SelectionState
import com.galyaminsky.zachetenot.ui.components.StageIndicator
import com.galyaminsky.zachetenot.ui.components.SubjectCard
import com.galyaminsky.zachetenot.ui.theme.Dimens
import com.galyaminsky.zachetenot.ui.theme.Primary
import com.galyaminsky.zachetenot.ui.theme.TextSecondary
import com.galyaminsky.zachetenot.ui.theme.ZachetEnotTheme

@Composable
fun SubjectSelectionScreen(
    selectedSubjectId: String?,
    onSubjectSelected: (String) -> Unit,
    onBack: () -> Unit,
    onContinue: () -> Unit,
    modifier: Modifier = Modifier
) {
    val subjects = AppCatalog.subjects
    Column(modifier = modifier.fillMaxSize().padding(horizontal = Dimens.ScreenHorizontalPadding)) {
        Column(modifier = Modifier.weight(1f).verticalScroll(rememberScrollState())) {
            Row(
                modifier = Modifier.fillMaxWidth().height(Dimens.Space48),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                BackButton(onClick = onBack)
                StageIndicator(current = 2, total = 2)
            }
            Spacer(modifier = Modifier.height(Dimens.Space24))
            Column(verticalArrangement = Arrangement.spacedBy(Dimens.Space12)) {
                InfoChip(text = stringResource(R.string.subject_grade_chip))
                Text(text = stringResource(R.string.subject_selection_title), style = MaterialTheme.typography.headlineLarge)
                Text(text = stringResource(R.string.subject_selection_subtitle), style = MaterialTheme.typography.bodyLarge, color = TextSecondary)
            }
            Spacer(modifier = Modifier.height(Dimens.Space24))
            Column(verticalArrangement = Arrangement.spacedBy(Dimens.Space12)) {
                subjects.forEach { subject ->
                    val title = when (subject.id) {
                        "russian" -> R.string.subject_russian
                        "math" -> R.string.subject_math
                        "literature" -> R.string.subject_literature
                        "world" -> R.string.subject_world
                        else -> error("Unknown subject: ${subject.id}")
                    }
                    val icon = when (subject.id) {
                        "math" -> R.drawable.ic_calculator
                        "literature" -> R.drawable.ic_book_open
                        "world" -> R.drawable.ic_globe
                        else -> null
                    }
                    SubjectCard(
                        title = stringResource(title),
                        subtitle = stringResource(if (subject.id == "russian") R.string.subject_russian_subtitle else R.string.subject_coming_soon),
                        state = when {
                            !subject.isAvailable -> SelectionState.Locked
                            selectedSubjectId == subject.id -> SelectionState.Selected
                            else -> SelectionState.Default
                        },
                        onClick = { if (subject.isAvailable) onSubjectSelected(subject.id) },
                        iconText = if (subject.id == "russian") "Aa" else null,
                        icon = icon
                    )
                }
            }
            Spacer(modifier = Modifier.height(Dimens.Space24))
            HintRow(
                icon = R.drawable.ic_sparkles,
                text = stringResource(R.string.subject_selection_hint),
                iconTint = Primary
            )
        }
        AppButton(
            text = stringResource(R.string.continue_label),
            onClick = onContinue,
            modifier = Modifier.fillMaxWidth(),
            enabled = subjects.any { it.id == selectedSubjectId && it.isAvailable },
            trailingIcon = R.drawable.ic_arrow_right
        )
        Text(
            text = stringResource(R.string.subject_selection_footer),
            style = MaterialTheme.typography.bodySmall,
            color = TextSecondary,
            modifier = Modifier.align(Alignment.CenterHorizontally).padding(top = Dimens.Space12, bottom = Dimens.Space24)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SubjectSelectionPreview() {
    ZachetEnotTheme {
        SubjectSelectionScreen(
            selectedSubjectId = AppCatalog.DEFAULT_SUBJECT_ID,
            onSubjectSelected = {},
            onBack = {},
            onContinue = {}
        )
    }
}
