package com.galyaminsky.zachetenot.ui.screens.home

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.galyaminsky.zachetenot.R
import com.galyaminsky.zachetenot.ui.components.AppNavigationBar
import com.galyaminsky.zachetenot.ui.components.NavItem
import com.galyaminsky.zachetenot.ui.components.StepProgressBar
import com.galyaminsky.zachetenot.ui.components.SettingsButton
import com.galyaminsky.zachetenot.ui.theme.Background
import com.galyaminsky.zachetenot.ui.theme.Accent
import com.galyaminsky.zachetenot.ui.theme.Dimens
import com.galyaminsky.zachetenot.ui.theme.Primary
import com.galyaminsky.zachetenot.ui.theme.OnPrimaryMuted
import com.galyaminsky.zachetenot.ui.theme.Selected
import com.galyaminsky.zachetenot.ui.theme.InfoContainer
import com.galyaminsky.zachetenot.ui.theme.Surface
import com.galyaminsky.zachetenot.ui.theme.TextPrimary
import com.galyaminsky.zachetenot.ui.theme.TextSecondary
import com.galyaminsky.zachetenot.ui.theme.ZachetEnotTheme

data class HomeSubjectUi(
    val id: String,
    val title: String,
    val subtitle: String,
    val available: Boolean,
    val selected: Boolean,
    val iconText: String? = null,
    @DrawableRes val icon: Int? = null
)

data class HomeUiState(
    val gradeLabel: String,
    val greeting: String,
    val programSummary: String,
    val ctaLabel: String,
    val subjects: List<HomeSubjectUi>,
    val passedCount: Int,
    val totalCount: Int,
    val percent: Int,
    val progressMessage: String
)

@Composable
fun HomeScreen(
    state: HomeUiState,
    onSettingsClick: () -> Unit,
    onProgramClick: () -> Unit,
    onSubjectClick: (String) -> Unit,
    onProgressClick: () -> Unit,
    modifier: Modifier = Modifier,
    onChangeGradeClick: () -> Unit = {},
    onSubjectsClick: () -> Unit = onProgramClick
) {
    val context = LocalContext.current
    val fact = remember(context) {
        context.assets.open("content/facts_ru.txt").bufferedReader().useLines { lines ->
            lines.firstOrNull { it.isNotBlank() }?.trim().orEmpty()
        }
    }
    Column(modifier = modifier.fillMaxSize().background(Background)) {
      Column(modifier = Modifier.weight(1f).verticalScroll(rememberScrollState()).padding(horizontal = Dimens.ScreenHorizontalPadding)) {
        Row(
            modifier = Modifier.fillMaxWidth().height(Dimens.Space48),
            horizontalArrangement = Arrangement.spacedBy(Dimens.Space12),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(painter = painterResource(R.drawable.ic_logo), contentDescription = null, modifier = Modifier.size(Dimens.Space32))
            Text(text = stringResource(R.string.home_brand), style = MaterialTheme.typography.titleLarge, color = TextPrimary, modifier = Modifier.weight(1f))
            Column(horizontalAlignment = Alignment.End, modifier = Modifier.clickable(onClick = onChangeGradeClick)) {
                Text(text = state.gradeLabel, style = MaterialTheme.typography.labelMedium, color = TextPrimary)
                Text(text = stringResource(R.string.home_change_grade), style = MaterialTheme.typography.bodySmall, color = Primary)
            }
            SettingsButton(onClick = onSettingsClick)
        }
        Spacer(modifier = Modifier.height(Dimens.Space16))
        Column(
            modifier = Modifier.fillMaxWidth().background(Selected, MaterialTheme.shapes.large).padding(Dimens.Space24),
            verticalArrangement = Arrangement.spacedBy(Dimens.Space16)
        ) {
            Text(text = state.greeting, style = MaterialTheme.typography.headlineLarge, color = TextPrimary)
            Text(text = state.programSummary, style = MaterialTheme.typography.bodyLarge, color = TextSecondary)
            Image(painter = painterResource(R.drawable.figma_rocket), contentDescription = null, modifier = Modifier.align(Alignment.CenterHorizontally))
            Button(onClick = onProgramClick, modifier = Modifier.fillMaxWidth().height(Dimens.Space48), colors = ButtonDefaults.buttonColors(containerColor = Accent, contentColor = TextPrimary)) {
                Text(text = stringResource(R.string.home_to_subjects), style = MaterialTheme.typography.labelLarge, color = TextPrimary)
            }
        }
        Spacer(modifier = Modifier.height(Dimens.Space16))
        Column(modifier = Modifier.fillMaxWidth().background(Primary, MaterialTheme.shapes.large).padding(start = Dimens.Space24, end = Dimens.Space24, top = Dimens.Space20, bottom = Dimens.Space8), verticalArrangement = Arrangement.spacedBy(Dimens.Space12)) {
            Text(text = stringResource(R.string.progress_title), style = MaterialTheme.typography.titleSmall, color = Surface)
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Column {
                    Text(text = stringResource(R.string.count_of_total, state.passedCount, state.totalCount), style = MaterialTheme.typography.headlineLarge, color = Surface)
                    Text(text = stringResource(R.string.progress_topics_done), style = MaterialTheme.typography.bodySmall, color = OnPrimaryMuted)
                }
                Text(text = stringResource(R.string.percent_format, state.percent), style = MaterialTheme.typography.headlineLarge.copy(fontSize = 24.sp), color = Accent)
            }
            StepProgressBar(progress = state.percent / 100f, trackColor = Surface.copy(alpha = 0.25f), fillColor = Accent)
            TextButton(onClick = onProgressClick, modifier = Modifier.fillMaxWidth().height(Dimens.Space48)) {
                Text(text = stringResource(R.string.home_view_progress), style = MaterialTheme.typography.labelLarge, color = Surface)
            }
        }
        Spacer(modifier = Modifier.height(Dimens.Space16))
        Row(modifier = Modifier.fillMaxWidth().background(InfoContainer, MaterialTheme.shapes.large).padding(Dimens.Space16), horizontalArrangement = Arrangement.spacedBy(Dimens.Space8), verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(Dimens.Space8)) {
                Text(text = stringResource(R.string.home_did_you_know), style = MaterialTheme.typography.labelSmall, color = Primary)
                Text(text = fact, style = MaterialTheme.typography.bodyLarge, color = TextPrimary)
            }
            Icon(painter = painterResource(R.drawable.ic_sparkles), contentDescription = null, modifier = Modifier.size(Dimens.Space32), tint = Primary)
        }
        Spacer(modifier = Modifier.height(Dimens.Space24))
      }
      AppNavigationBar(
          items = listOf(
              NavItem(stringResource(R.string.home_nav_home), R.drawable.ic_logo),
              NavItem(stringResource(R.string.home_nav_subjects), R.drawable.ic_layers),
              NavItem(stringResource(R.string.nav_progress), R.drawable.ic_chart)
          ),
          selectedIndex = 0,
          onSelect = { index -> when (index) { 1 -> onSubjectsClick(); 2 -> onProgressClick() } }
      )
    }
}

@Composable
private fun previewSubjects(multipleAvailable: Boolean): List<HomeSubjectUi> = listOf(
    HomeSubjectUi("russian", stringResource(R.string.subject_russian), stringResource(R.string.subject_russian_subtitle), true, true, iconText = stringResource(R.string.subject_russian_icon)),
    HomeSubjectUi("math", stringResource(R.string.subject_math), stringResource(R.string.subject_coming_soon), multipleAvailable, false, icon = R.drawable.ic_calculator),
    HomeSubjectUi("literature", stringResource(R.string.subject_literature), stringResource(R.string.subject_coming_soon), false, false, icon = R.drawable.ic_book_open),
    HomeSubjectUi("world", stringResource(R.string.subject_world), stringResource(R.string.subject_coming_soon), false, false, icon = R.drawable.ic_globe)
)

@Composable
private fun HomePreviewContent(passedCount: Int, percent: Int, multipleAvailable: Boolean) {
    ZachetEnotTheme {
        HomeScreen(
            state = HomeUiState(
                gradeLabel = stringResource(R.string.subject_grade_chip),
                greeting = stringResource(if (passedCount == 0) R.string.home_start_greeting else R.string.topics_greeting),
                programSummary = stringResource(if (passedCount == 0) R.string.subject_selection_footer else R.string.home_continue_summary),
                ctaLabel = stringResource(R.string.home_to_subjects),
                subjects = previewSubjects(multipleAvailable),
                passedCount = passedCount,
                totalCount = 6,
                percent = percent,
                progressMessage = stringResource(if (passedCount == 0) R.string.subject_selection_footer else R.string.topics_progress_message)
            ),
            onSettingsClick = {}, onProgramClick = {}, onSubjectClick = {}, onProgressClick = {}, onChangeGradeClick = {}
        )
    }
}

@Preview(showBackground = true, heightDp = 1000)
@Composable
private fun FirstVisitPreview() = HomePreviewContent(passedCount = 0, percent = 0, multipleAvailable = false)

@Preview(showBackground = true, heightDp = 1000)
@Composable
private fun ContinuePreview() = HomePreviewContent(passedCount = 2, percent = 33, multipleAvailable = false)

