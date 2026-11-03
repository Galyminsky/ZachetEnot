package com.galyaminsky.zachetenot.ui.screens.classselection

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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.stringResource
import com.galyaminsky.zachetenot.R
import com.galyaminsky.zachetenot.domain.catalog.AppCatalog
import com.galyaminsky.zachetenot.ui.components.AppButton
import com.galyaminsky.zachetenot.ui.components.AppLogo
import com.galyaminsky.zachetenot.ui.components.ClassCard
import com.galyaminsky.zachetenot.ui.components.HintRow
import com.galyaminsky.zachetenot.ui.components.SelectionState
import com.galyaminsky.zachetenot.ui.components.StageIndicator
import com.galyaminsky.zachetenot.ui.theme.TextSecondary

@Composable
fun ClassSelectionScreen(
    selectedGrade: Int?,
    onGradeSelected: (Int) -> Unit,
    onContinue: () -> Unit,
    modifier: Modifier = Modifier
) {
    val grades = AppCatalog.grades
    val illustrations = listOf(
        R.drawable.illustration_locked,
        R.drawable.illustration_locked_2,
        R.drawable.illustration_school,
        R.drawable.illustration_school_locked
    )
    Column(modifier = modifier.fillMaxSize().padding(horizontal = 24.dp)) {
        Column(modifier = Modifier.weight(1f).verticalScroll(rememberScrollState())) {
            Row(
                modifier = Modifier.fillMaxWidth().height(34.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                AppLogo()
                StageIndicator(current = 1, total = 2)
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = stringResource(R.string.figma_class_title), style = MaterialTheme.typography.headlineLarge)
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = stringResource(R.string.figma_class_subtitle), style = MaterialTheme.typography.bodyLarge, color = TextSecondary)
            Spacer(modifier = Modifier.height(24.dp))
            grades.chunked(2).forEachIndexed { rowIndex, rowGrades ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    rowGrades.forEachIndexed { columnIndex, grade ->
                        ClassCard(
                            grade = grade.number,
                            state = when {
                                !grade.isAvailable -> SelectionState.Locked
                                selectedGrade == grade.number -> SelectionState.Selected
                                else -> SelectionState.Default
                            },
                            illustration = illustrations[rowIndex * 2 + columnIndex],
                            onClick = { if (grade.isAvailable) onGradeSelected(grade.number) },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
                if (rowIndex == 0) Spacer(modifier = Modifier.height(12.dp))
            }
            Spacer(modifier = Modifier.height(20.dp))
            HintRow(icon = R.drawable.ic_lock, text = stringResource(R.string.figma_class_hint))
        }
        AppButton(
            text = stringResource(R.string.continue_label),
            onClick = onContinue,
            modifier = Modifier.fillMaxWidth(),
            enabled = grades.any { it.number == selectedGrade && it.isAvailable },
            trailingIcon = R.drawable.ic_arrow_right
        )
        Text(
            text = stringResource(R.string.figma_class_footer),
            style = MaterialTheme.typography.bodySmall,
            color = TextSecondary,
            modifier = Modifier.align(Alignment.CenterHorizontally).padding(top = 12.dp, bottom = 24.dp)
        )
    }
}
